package hillClimbing;

import hillClimbing.entities.Board;
import hillClimbing.test.Input;
import hillClimbing.test.Output;
import hillClimbing.utils.FitnessScore;
import hillClimbing.utils.Population;

import java.util.*;

public class QueenHillClimbing {
    public static final int PLATEAU_STEPS_LIMIT = 64;

    public static void analyze(int k) {
        HashMap<Output.OutputType, Integer> results = new HashMap<>();

        Board[] population = Population.generateAll(k);

        for (Board instance : population) {
            Output.OutputType result = goal(instance);
            if (results.containsKey(result)) {
                Integer frequency = results.get(result);
                results.put(result, frequency + 1);
            } else {
                results.put(result, 1);
            }
        }

        results.forEach((outputType, frequency) -> System.out.println(outputType + ": " + frequency));
    }

    public static Output.OutputType goal(Board instance) {
        int plateauSteps = 0;

        int minHeight = FitnessScore.evaluate(instance);

        Board[] population = instance.expand();

        while (true) {
            Optional<Board> maybePeek = Population.peek(population);
            if (maybePeek.isPresent()) {
                Board peek = maybePeek.get();
                System.out.println("Peek instance: " + peek);
                return Output.OutputType.GLOBAL_MAXIMA;
            }

            Optional<Board> maybeNext = Population.min(population);
            if (maybeNext.isEmpty()) {
                return Output.OutputType.UNKNOWN;
            }

            Board next = maybeNext.get();
            int nextHeight = FitnessScore.evaluate(next);
            if (nextHeight > minHeight) {
                return Output.OutputType.LOCAL_MAXIMA;
            } else if (nextHeight == minHeight) {
                if (plateauSteps >= PLATEAU_STEPS_LIMIT) {
                    return Output.OutputType.PLATEAU;
                } else {
                    ++plateauSteps;
                }
            } else {
                population = next.expand();
                minHeight = nextHeight;
                plateauSteps = 0;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(Input.TEST_8x8);

        int k = scanner.nextInt();

        Board instance = Population.generateOne(k);

        Output.OutputType result = goal(instance);
        Output.print(result);

        analyze(k);
    }
}
