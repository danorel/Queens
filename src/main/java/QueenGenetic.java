import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

import entities.Board;
import utils.Population;
import utils.Selection;

import static test.Input.TEST_8x8;

class QueenGenetic {
    public static boolean life(int k) {
        int generations = 1024;

        Board[] population = Population.generateAll(k);

        for (int generation = 0; generation < generations; ++generation) {
            Optional<Board> uber = Population.uber(population);
            if (uber.isPresent()) {
                System.out.println("Population has achieved the goal by " + generation + " generation");
                System.out.println("Uber instance: " + uber);
                return true;
            }
            population = Selection.reproduce(population);
        }

        return false;
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(TEST_8x8);

        int k = scanner.nextInt();

        if (!life(k)) {
            System.out.println("Population has failed to find the goal");
        }
    }
}
