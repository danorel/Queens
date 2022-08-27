package evolutionary.utils;

import evolutionary.entities.Board;

import java.util.HashSet;
import java.util.Set;

public class Population {
    public static Board[] generateAll(int k) {
        Set<String> cache = new HashSet<>();
        int size = k * k;
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
