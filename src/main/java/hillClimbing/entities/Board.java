package hillClimbing.entities;

import org.apache.commons.math3.util.Pair;
import java.util.*;

public class Board {
    private static final int[][] vectors = new int[][]{
            new int[]{ 0, 1 },
            new int[]{ 1, 0 },
            new int[]{ -1, 0 },
            new int[]{ 0, -1 },
            new int[]{ 1, -1 },
            new int[]{ -1, 1 },
            new int[]{ 1, 1 },
            new int[]{ -1, -1 }
    };

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

    public Board random() {
        ArrayList<Pair<Integer, Integer>> coordinates = this.getQueens();

        int randomCoordinate = Math.toIntExact(Math.round(Math.random() * (coordinates.size() - 1)));

        Pair<Integer, Integer> coordinate = coordinates.get(randomCoordinate);

        int x = coordinate.getFirst();
        int y = coordinate.getSecond();

        Board[] successors = expandByCoordinates(x, y);

        int randomSuccessor = Math.toIntExact(Math.round(Math.random() * (successors.length - 1)));

        return successors[randomSuccessor];
    }

    public Board[] expand() {
        ArrayList<Board> instances = new ArrayList<>();

        HashSet<String> cache = new HashSet<>();

        ArrayList<Pair<Integer, Integer>> coordinates = this.getQueens();

        for (Pair<Integer, Integer> coordinate : coordinates) {
            int x = coordinate.getFirst();
            int y = coordinate.getSecond();

            for (Board instance : expandByCoordinates(x, y)) {
                String instanceDNK = instance.getDNK();
                if (!cache.contains(instanceDNK)) {
                    instances.add(instance);
                    cache.add(instanceDNK);
                }
            }
        }

        return instances.toArray(new Board[0]);
    }

    private Board[] expandByCoordinates(int x, int y) {
        ArrayList<Board> instances = new ArrayList<>();

        for (int[] vector : vectors) {
            instances.addAll(List.of(expandByCoordinatesAndVector(x, y, vector)));
        }

        return instances.toArray(new Board[0]);
    }

    private Board[] expandByCoordinatesAndVector(int x, int y, int[] vector) {
        ArrayList<Board> instances = new ArrayList<>();

        Queue<int[]> queue = new LinkedList<>(Collections.singleton(new int[]{ x, y }));

        while (!queue.isEmpty()) {
            int[] coordinates = queue.poll();

            int r = coordinates[0] + vector[0];
            int c = coordinates[1] + vector[1];

            if (r < 0 || r >= this.k || c < 0 || c >= this.k) {
                continue;
            }

            if (this.cells[r][c] != QUEEN) {
                Board instance = new Board(this);
                instance.cells[coordinates[0]][coordinates[1]] = EMPTY;
                instance.cells[r][c] = QUEEN;
                instances.add(instance);
            }

            queue.add(new int[]{ r, c });
        }

        return instances.toArray(new Board[0]);
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
