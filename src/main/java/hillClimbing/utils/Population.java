package hillClimbing.utils;

import hillClimbing.entities.Board;
import org.apache.commons.math3.util.Pair;

import java.util.*;

public class Population {
    public static Optional<Board> peek(Board[] population) {
        return Arrays.stream(population).filter(maybeUber -> FitnessScore.evaluate(maybeUber) == 0).findFirst();
    }

    public static Optional<Board> min(Board[] population) {
        return Arrays.stream(population).min(FitnessScore::compare);
    }

    public static Optional<Board> stochasticPick(Board[] population, Board crossoverPoint) {
        int crossoverHeight = FitnessScore.evaluate(crossoverPoint);

        Board[] steeperThenInstance = Arrays.stream(population).filter(identity -> FitnessScore.evaluate(identity) < crossoverHeight).toArray(Board[]::new);
        
        double totalHeight = Arrays.stream(steeperThenInstance).mapToDouble(FitnessScore::evaluate).reduce(0, Double::sum);

        if (totalHeight == 0) {
            return Optional.empty();
        }

        HashMap<Board, Pair<Double, Double>>
                stepperInstanceProbabilities = new HashMap<>();

        double probabilityStart = 0;
        for (Board steeperInstance : steeperThenInstance) {
            double steeperHeight = FitnessScore.evaluate(steeperInstance);
            double probabilityEnd = probabilityStart + (steeperHeight / totalHeight);
            stepperInstanceProbabilities.put(steeperInstance, new Pair<>(probabilityStart, probabilityEnd));
            probabilityStart = probabilityEnd;
        }

        double random = Math.random();

        return stepperInstanceProbabilities.entrySet().stream().filter((entry) -> {
            Pair<Double, Double> probability = entry.getValue();
            double start = probability.getFirst();
            double end = probability.getSecond();
            return random >= start && random <= end;
        }).map(Map.Entry::getKey).findFirst();
    }

    public static Board generateOne(int k) {
        return generate(k);
    }

    public static Board[] generateAll(int k) {
        Set<String> cache = new HashSet<>();
        int size = k * k * k;
        Board[] boards = new Board[size];
        int i = 0;
        while (i < size) {
            Board board = generate(k);
            String DNK = board.getDNK();
            if (!cache.contains(DNK)) {
                boards[i++] = board;
                cache.add(DNK);
            }
        }
        return boards;
    }

    private static Board generate(int k) {
        Set<Integer> cache = new HashSet<>();
        StringBuilder DNK = new StringBuilder();
        while (DNK.length() != k) {
            int random = Math.toIntExact(Math.round(Math.random() * (k - 1)));
            if (!cache.contains(random)) {
                DNK.append(random);
                cache.add(random);
            }
        }
        return Board.getInstance(DNK.toString());
    }
}
