package evolutionary;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import evolutionary.entities.Board;
import evolutionary.test.Input;
import evolutionary.utils.Population;
import evolutionary.utils.Search;
import evolutionary.utils.Selection;

class QueenEvolutionary {
    public static final int NOT_FOUND = -1;
    public static final int GENERATIONS = 64;

    public static void analyze(int k) {
        int bestGeneration = Integer.MAX_VALUE;

        ArrayList<int[]> configs = new ArrayList<>();

        Board[] initialPopulation = Population.generateAll(k);

        int successorsMin = 1, successorsMax = 10;
        int mutationMin = 0, mutationMax = 100;
        int cullingMin = 1, cullingMax = 99;

        for (int successors = successorsMin; successors < successorsMax; ++successors) {
            for (int mutation = mutationMin; mutation < mutationMax; mutation += k * k) {
                for (int culling = cullingMin; culling < cullingMax; culling += k * k) {
                    Board[] population = initialPopulation.clone();
                    int possiblyBetterGeneration = goal(population, successors, mutation, culling);
                    if (possiblyBetterGeneration != NOT_FOUND) {
                        int[] possiblyBetterConfig = new int[] { possiblyBetterGeneration, successors, mutation, culling };
                        if (possiblyBetterGeneration < bestGeneration) {
                            configs.clear();
                            configs.add(possiblyBetterConfig);
                            bestGeneration = possiblyBetterGeneration;
                        } else if (possiblyBetterGeneration == bestGeneration) {
                            configs.add(possiblyBetterConfig);
                        }
                    }
                }
            }
        }

        System.out.println("Most effective population configurations with " + bestGeneration + " generation:");
        for (int i = 1; i <= configs.size(); ++i) {
            int[] config = configs.get(i - 1);
            System.out.println("Config #" + i + ": (" + "successors: " + config[1] + ", mutation: " + config[2] + ", culling: " + config[3] + ")");
        }
    }

    public static int goal(Board[] population, int successors, int mutation, int culling) {
        for (int generation = 1; generation <= GENERATIONS; ++generation) {
            Optional<Board> uber = Search.uber(population);
            if (uber.isPresent()) {
                System.out.println("Uber instance: " + uber);
                return generation;
            }
            population = Selection.reproduce(population, successors, mutation, culling);
        }

        return NOT_FOUND;
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(Input.TEST_6x6_3xSUCCESSORS_30xMUTATION_75xCULLING);

        int k = scanner.nextInt();
        int successors = scanner.nextInt();
        int mutation = scanner.nextInt();
        int culling = scanner.nextInt();

        Board[] population = Population.generateAll(k);

        int result = goal(population, successors, mutation, culling);

        if (result == NOT_FOUND) {
            System.out.println("Population has failed to find the goal");
        } else {
            System.out.println("Population has achieved the goal by " + result + " generation (successors: " + successors + ", mutation: " + mutation + ", culling: " + culling + ")");
        }

        analyze(k);
    }
}
