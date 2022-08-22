package utils;

import entities.Board;

import java.util.HashSet;
import java.util.Set;

public class Population {
    public static final int SIZE = 64;

    public static Board[] generateAll(int k) {
        Set<String> cache = new HashSet<>();
        Board[] boards = new Board[SIZE];
        int i = 0;
        while (i < SIZE) {
            Board board = generate(k);
            String DNK = Board.encode(board);
            if (!cache.contains(DNK)) {
                boards[i++] = board;
                cache.add(DNK);
            }
        }
        return boards;
    }

    public static Board generateOne(int k) {
        return generate(k);
    }

    private static Board generate(int k) {
        Set<Integer> cache = new HashSet<>();
        StringBuilder DNK = new StringBuilder();
        while (DNK.length() != k) {
            int random = Math.toIntExact(Math.round(1 + Math.random() * (k - 1)));
            if (!cache.contains(random)) {
                DNK.append(random);
                cache.add(random);
            }
        }
        return Board.decode(DNK.toString());
    }
}
