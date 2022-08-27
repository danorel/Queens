package hillClimbing;

import hillClimbing.entities.Board;
import hillClimbing.test.Input;
import hillClimbing.test.Output;
import hillClimbing.utils.FitnessScore;
import hillClimbing.utils.Population;

import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

public class QueenFirstChoiceHillClimbing {
    public static void analyze(int k) {
        HashMap<Output.OutputType, Integer> report = new HashMap<>();

        Board[] population = Population.generateAll(k);

        for (Board instance : population) {
            Output.OutputType result = goal(instance);
            if (report.containsKey(result)) {
                Integer frequency = report.get(result);
                report.put(result, frequency + 1);
            } else {
                report.put(result, 1);
            }
        }

        report.forEach((output, frequency) -> System.out.println(output + ": " + frequency));
    }

    public static Output.OutputType goal(Board instance) {
        Board[] population = instance.expand();
        int k = instance.getK();

        int maxPlateau = k * k, plateau = 0;
        int minHeight = FitnessScore.evaluate(instance);

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
                if (plateau >= maxPlateau) {
                    return Output.OutputType.PLATEAU;
                } else {
                    population = next.expand();
                    ++plateau;
                }
            } else {
                population = next.expand();
                minHeight = nextHeight;
                plateau = 0;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(Input.TEST_7x7);

        int k = scanner.nextInt();

        Board instance = Population.generateOne(k);
        Output.print(goal(instance));

        analyze(k);
    }
}
