package evolutionary.utils;

import java.util.*;

import evolutionary.entities.Board;

public class Selection {

    public static PriorityQueue<Board> elitism(Board[] prevGeneration, int culling) {
        if (culling < 0 || culling > 100) {
            throw new RuntimeException("Culling factor is not between 0 and 100");
        }

        int eliteSize = (int) Math.round(prevGeneration.length * (double)(1 - culling / 100));

        PriorityQueue<Board> eliteQueue = new PriorityQueue<>(Comparator.comparingInt(FitnessScore::evaluate));
        eliteQueue.addAll(Arrays.asList(prevGeneration).subList(0, eliteSize));

        return eliteQueue;
    }

    public static Board[] reproduce(Board[] prevGeneration, int successors, int mutationRate, int culling) {
        PriorityQueue<Board> eliteGeneration = Selection.elitism(prevGeneration, culling);

        ArrayList<Board> nextGeneration = new ArrayList<>(eliteGeneration.size() * successors);

        while (!eliteGeneration.isEmpty()) {
            Board eliteFather = eliteGeneration.poll();
            assert eliteFather != null;
            Board eliteMother = eliteGeneration.poll();
            if (eliteMother == null) {
                break;
            }

            for (int successor = 0; successor < successors; ++successor) {
                Board[] children = Selection.crossing(eliteFather, eliteMother, successors, mutationRate);
                nextGeneration.addAll(List.of(children));
            }
        }

        return nextGeneration.toArray(new Board[0]);
    }

    public static Board[] crossing(Board father, Board mother, int successors, int mutationRate) {
        Board[] children = new Board[successors];

        String fatherDNK = father.getDNK();
        String motherDNK = mother.getDNK();

        if (fatherDNK.length() != motherDNK.length()) {
            throw new RuntimeException("Parent's DNK has not the same size");
        }

        for (int successor = 0; successor < successors; ++successor) {
            String childDNK = cross(fatherDNK, motherDNK);
            String childDNKMutated = mutate(childDNK, mutationRate);

            children[successor] = Board.getInstance(childDNKMutated);
        }

        return children;
    }

    private static String cross(String fatherDNK, String motherDNK) {
        int childDNKLength = Math.min(fatherDNK.length(), motherDNK.length());
        int crossoverPoint = Math.toIntExact(Math.round(Math.random() * childDNKLength));
        return fatherDNK.substring(0, crossoverPoint) + motherDNK.substring(crossoverPoint);
    }

    private static String mutate(String DNK, int mutationRate) {
        if (mutationRate < 0 || mutationRate > 100) {
            throw new RuntimeException("Culling factor is not between 0 and 100");
        }

        ArrayList<Integer> digits = new ArrayList<>();
        for (char digit : DNK.toCharArray()) {
            digits.add(Character.digit(digit, 10));
        }

        StringBuilder DNKCopy = new StringBuilder(DNK);

        for (int index = 0; index < DNK.length(); ++index) {
            int decision = Math.toIntExact(Math.round((Math.random() * 100)));

            if (decision <= mutationRate) {
                int randomIndex = Math.toIntExact(Math.round((Math.random() * (digits.size() - 1))));
                int randomDigit = digits.get(randomIndex);
                DNKCopy.setCharAt(index, (char) (randomDigit + '0'));
            }
        }

        return DNKCopy.toString();
    }
}

