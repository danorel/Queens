package entities;

public class Board {

    public static final char QUEEN = 'q';
    public static final char EMPTY = '-';

    private int k;
    private char[][] cells;

    public Board(int k, char[][] cells) {
        this.k = k;
        this.cells = new char[k][k];
        for (int i = 0; i < k; ++k) {
            for (int j = 0; j < k; ++j) {
                this.cells[i][j] = cells[i][j];
            }
        }
    }

    public static String encode(char[][] cells) {
        StringBuilder DNK = new StringBuilder();
        for (int i = 0; i < cells.length; ++k) {
            for (int j = 0; j < cells[i].length; ++j) {
                if (cells[i][j] == Board.QUEEN) {
                    DNK.append(String.valueOf(j));
                }
            }
        }
        return DNK.toString();
    }

    public static char[][] decode(String DNK) {
        int k = DNK.length();
        char[][] cells = new char[k][k];
        for (int i = 0; i < k; ++k) {
            for (int j = 0; j < k; ++j) {
                cells[i][j] = Board.EMPTY;
            }
        }
        for (int c = 0; c < k; ++c) {
            int r = Integer.parseInt(String.valueOf(cells[c]));
            cells[r][c] = Board.QUEEN;
        }
        return cells;
    }
}
