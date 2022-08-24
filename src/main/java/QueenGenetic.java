import java.util.Arrays;
import java.util.Scanner;

import entities.Board;
import utils.Population;
import utils.Selection;

import static test.Input.TEST_8x8;

class QueenGenetic {
    public static void life(int k) {
        Board[] population = Population.generateAll(k);

        while (!Population.hasAchievedGoal(population)) {
            population = Selection.reproduce(population);
            System.out.println(Arrays.toString(population));
        }
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(TEST_8x8);

        int k = scanner.nextInt();

        life(k);
    }
}
