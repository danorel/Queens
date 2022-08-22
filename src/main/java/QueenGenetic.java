import java.util.Arrays;
import java.util.Scanner;

import entities.Board;
import utils.Population;

import static test.Input.TEST_8x8;

class QueenGenetic {
    public static void main(String[] args){
        Scanner scanner = new Scanner(TEST_8x8);

        int k = scanner.nextInt();

        Board[] population = Population.generateAll(k);

        System.out.println(Arrays.toString(population));
    }
}