import java.util.Optional;
import java.util.Scanner;

import entities.Board;
import test.Input;
import utils.Population;
import utils.Selection;

class QueenGenetic {
    public static final int generations = 64;

    public static void analyze(int k) {
        // Tuple of effective generation: [generation, successors, culling]
        int[] effectiveConfiguration = new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE };

        Board[] initialPopulation = Population.generateAll(k);

        int successorsMin = 1, successorsMax = 10;
        int cullingMin = 1, cullingMax = 99;

        for (int successors = successorsMin; successors < successorsMax; successors += Math.toIntExact((long) Math.sqrt(k))) {
            for (int culling = cullingMin; culling < cullingMax; culling += Math.toIntExact((long) Math.sqrt(k))) {
                Board[] population = initialPopulation.clone();
                int currentGeneration = goal(population, successors, culling);
                if (currentGeneration != -1) {
                    if (currentGeneration < effectiveConfiguration[0]) {
                        effectiveConfiguration[0] = currentGeneration;
                        effectiveConfiguration[1] = successors;
                        effectiveConfiguration[2] = culling;
                    }
                }
            }
        }

        System.out.println("Most effective population configuration:");
        System.out.println("Population: " + effectiveConfiguration[0]);
        System.out.println("Successors: " + effectiveConfiguration[1]);
        System.out.println("Culling: " + effectiveConfiguration[2]);
    }

    public static int goal(Board[] population, int successors, int culling) {
        for (int generation = 1; generation <= generations; ++generation) {
            Optional<Board> uber = Population.uber(population);
            if (uber.isPresent()) {
                System.out.println("Population has achieved the goal by " + generation + " generation (successors: " + successors + ", culling: " + culling + ")");
                System.out.println("Uber instance: " + uber);
                return generation;
            }
            population = Selection.reproduce(population, successors, culling);
        }

        return -1;
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(Input.TEST_8x8_3xSUCCESSORS_75xCULLING);

        int k = scanner.nextInt();
        int successors = scanner.nextInt();
        int culling = scanner.nextInt();

        Board[] population = Population.generateAll(k);

        if (goal(population, successors, culling) == -1) {
            System.out.println("Population has failed to find the goal");
        }

        analyze(k);
    }
}
