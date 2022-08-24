package utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import entities.Board;

public class Selection {

    public static PriorityQueue<Board> elitism(Board[] prevGeneration) {
        return Selection.elitism(prevGeneration, 50);
    }

    public static PriorityQueue<Board> elitism(Board[] prevGeneration, int culling) {
        if (culling < 0 || culling > 100) {
            throw new RuntimeException("Culling factor is not between 0 and 100");
        }

        int eliteSize = (int) Math.round(prevGeneration.length * (double)(1 - culling / 100));

        PriorityQueue<Board> eliteQueue = new PriorityQueue<>(eliteSize, Comparator.comparingInt(FitnessScore::evaluate));

        for (int i = 0; i < eliteSize; i++) {
            eliteQueue.add(prevGeneration[i]);
        }

        return eliteQueue;
    }

    public static Board[] reproduce(Board[] prevGeneration) {
        return Selection.reproduce(prevGeneration, 2, 50);
    }

    public static Board[] reproduce(Board[] prevGeneration, int successors, int culling) {
        PriorityQueue<Board> eliteGeneration = Selection.elitism(prevGeneration, culling);

        ArrayList<Board> nextGeneration = new ArrayList<>(eliteGeneration.size() * successors);

        while (!eliteGeneration.isEmpty()) {
            Board eliteFather = eliteGeneration.poll();
            assert eliteFather != null;
            Board eliteMother = eliteGeneration.poll();
            assert eliteMother != null;

            for (int successor = 0; successor < successors; ++successor) {
                Board child = Selection.crossing(eliteFather, eliteMother);
                nextGeneration.add(child);
            }
        }

        return nextGeneration.toArray(new Board[0]);
    }

    public static Board crossing(Board father, Board mother) {
        String fatherDNK = father.getDNK();
        String motherDNK = mother.getDNK();

        int childDNKLength = Math.min(fatherDNK.length(), motherDNK.length());

        if (fatherDNK.length() != childDNKLength || motherDNK.length() != childDNKLength) {
            throw new RuntimeException("Parent's DNK has not the same size");
        }

        int crossoverPoint = Math.toIntExact(Math.round(Math.random() * childDNKLength));

        String childDNK = fatherDNK.substring(0, crossoverPoint) + motherDNK.substring(crossoverPoint);

        return Board.getInstance(childDNK);
    }
}

