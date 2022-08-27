package evolutionary.entities;

import java.util.*;
import org.apache.commons.math3.util.Pair;

public class Board {
    public static final char QUEEN = 'q';
    public static final char EMPTY = '-';

    private int k;
    private char[][] cells;

    public Board(Board that) {
        this(that.k, that.cells);
    }

    public Board(int k, char[][] cells) {
        this.k = k;
        this.cells = new char[k][k];
        for (int i = 0; i < k; ++i) {
            for (int j = 0; j < k; ++j) {
                this.cells[i][j] = cells[i][j];
            }
        }
    }

    public String getDNK() {
        StringBuilder DNK = new StringBuilder();
        for (int i = 0; i < this.cells.length; ++i) {
            for (int j = 0; j < this.cells[i].length; ++j) {
                if (this.cells[i][j] == Board.QUEEN) {
                    DNK.append(j);
                }
            }
        }
        return DNK.toString();
    }

    public static Board getInstance(String DNK) {
        int k = DNK.length();
        char[][] cells = new char[k][k];
        for (int i = 0; i < k; ++i) {
            for (int j = 0; j < k; ++j) {
                cells[i][j] = Board.EMPTY;
            }
        }
        for (int c = 0; c < k; ++c) {
            int r = Integer.parseInt(String.valueOf(DNK.charAt(c)));
            cells[r][c] = Board.QUEEN;
        }
        return new Board(k, cells);
    }

    public ArrayList<Pair<Integer, Integer>> getQueens() {
        ArrayList<Pair<Integer, Integer>> coordinates = new ArrayList<>();

        for (int i = 0; i < k; ++i) {
            for (int j = 0; j < k; ++j) {
                if (cells[i][j] == QUEEN) {
                    coordinates.add(new Pair<>(i, j));
                }
            }
        }

        return coordinates;
    }

    public int getK() {
        return k;
    }

    public char[][] getCells() {
        return cells;
    }

    @Override
    public String toString() {
        return "Board{" +
                "k=" + k +
                ", cells=" + Arrays.deepToString(cells) +
                '}';
    }
}
