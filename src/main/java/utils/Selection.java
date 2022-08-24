package utils;

import java.util.*;

import entities.Board;

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

    public static Board[] reproduce(Board[] prevGeneration, int successors, int culling) {
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
                Board[] children = Selection.crossing(eliteFather, eliteMother, successors);
                nextGeneration.addAll(List.of(children));
            }
        }

        return nextGeneration.toArray(new Board[0]);
    }

    public static Board[] crossing(Board father, Board mother, int successors) {
        Board[] children = new Board[successors];

        String fatherDNK = father.getDNK();
        String motherDNK = mother.getDNK();

        int childDNKLength = Math.min(fatherDNK.length(), motherDNK.length());

        if (fatherDNK.length() != childDNKLength || motherDNK.length() != childDNKLength) {
            throw new RuntimeException("Parent's DNK has not the same size");
        }

        for (int successor = 0; successor < successors; ++successor) {
            int crossoverPoint = Math.toIntExact(Math.round(Math.random() * childDNKLength));

            String childDNK = fatherDNK.substring(0, crossoverPoint) + motherDNK.substring(crossoverPoint);

            children[successor] = Board.getInstance(childDNK);
        }

        return children;
    }
}

