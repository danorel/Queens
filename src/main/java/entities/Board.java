package entities;

import java.util.Arrays;

public class Board {

    public static final char QUEEN = 'q';
    public static final char EMPTY = '-';

    private int k;
    private char[][] cells;

    public Board(int k, char[][] cells) {
        this.k = k;
        this.cells = new char[k][k];
        for (int i = 0; i < k; ++i) {
            for (int j = 0; j < k; ++j) {
                this.cells[i][j] = cells[i][j];
            }
        }
    }

    public static String encode(Board board) {
        StringBuilder DNK = new StringBuilder();
        for (int i = 0; i < board.cells.length; ++i) {
            for (int j = 0; j < board.cells[i].length; ++j) {
                if (board.cells[i][j] == Board.QUEEN) {
                    DNK.append(String.valueOf(j));
                }
            }
        }
        return DNK.toString();
    }

    public static Board decode(String DNK) {
        int k = DNK.length();
        char[][] cells = new char[k][k];
        for (int i = 0; i < k; ++i) {
            for (int j = 0; j < k; ++j) {
                cells[i][j] = Board.EMPTY;
            }
        }
        for (int c = 0; c < k; ++c) {
            int r = Integer.parseInt(String.valueOf(DNK.charAt(c))) - 1;
            cells[r][c] = Board.QUEEN;
        }
        return new Board(k, cells);
    }

    @Override
    public String toString() {
        return "Board{" +
                "k=" + k +
                ", cells=" + Arrays.deepToString(cells) +
                '}';
    }
}
