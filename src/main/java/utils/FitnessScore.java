package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.math3.util.Pair;

import entities.Board;

public class FitnessScore {

    static final int[][] vectors = new int[][]{
            new int[]{ 0, 1 },
            new int[]{ 1, 0 },
            new int[]{ -1, 0 },
            new int[]{ 0, -1 },
            new int[]{ 1, -1 },
            new int[]{ -1, 1 },
            new int[]{ 1, 1 },
            new int[]{ -1, -1 }
    };

    public static int evaluate(Board board) {
        int rate = 0;

        ArrayList<Pair<Integer, Integer>> queens = board.getQueens();

        for (Pair<Integer, Integer> queen : queens) {
            int r = queen.getFirst();
            int c = queen.getSecond();

            rate += starCount(board, r, c);
        }

        return rate;
    }

    private static int starCount(Board board, int x, int y) {
        int rate = 0;

        int k = board.getK();
        char[][] cells = board.getCells();

        for (int[] vector : vectors) {
            rate += starCountIterative(k, cells, x, y, vector);
        }

        return rate;
    }

    private static int starCountIterative(int k, char[][] cells, int x, int y, int[] vector) {
        int rate = 0;

        Queue<int[]> queue = new LinkedList<>(Collections.singleton(new int[]{ x, y }));

        while (!queue.isEmpty()) {
            int[] coordinates = queue.poll();

            int r = coordinates[0] + vector[0];
            int c = coordinates[1] + vector[1];

            if (r < 0 || r >= k || c < 0 || c >= k) {
                return rate;
            }

            if (cells[r][c] == Board.QUEEN) {
                ++rate;
            }

            queue.add(new int[]{ r, c });
        }

        return rate;
    }
}
