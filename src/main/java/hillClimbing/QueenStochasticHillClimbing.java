package hillClimbing;

import java.util.*;
import org.apache.commons.math3.util.Pair;
import org.jetbrains.annotations.Nullable;

import hillClimbing.abstractions.QueenHillClimbingSolver;
import hillClimbing.entities.Board;
import hillClimbing.test.Input;
import hillClimbing.test.Output;
import hillClimbing.utils.FitnessScore;
import hillClimbing.utils.Population;

public class QueenStochasticHillClimbing extends QueenHillClimbingSolver {

    @Override
    public Optional<Board> search(Board[] successorBoards, @Nullable Board boundaryBoard) {
        int boundaryHeight = FitnessScore.evaluate(boundaryBoard);

        Board[] steeperBoards = Arrays.stream(successorBoards).filter(successorBoard -> FitnessScore.evaluate(successorBoard) < boundaryHeight).toArray(Board[]::new);

        double steeperTotalHeight = Arrays.stream(steeperBoards).mapToDouble(FitnessScore::evaluate).reduce(0, Double::sum);

        if (steeperTotalHeight == 0) {
            return Optional.empty();
        }

        HashMap<Board, Pair<Double, Double>>
                steeperBoardsProbabilities = new HashMap<>();

        double probabilityStart = 0;
        for (Board steeperBoard : steeperBoards) {
            double steeperHeight = FitnessScore.evaluate(steeperBoard);
            double probabilityMargin = steeperHeight / steeperTotalHeight;
            double probabilityEnd = probabilityStart + probabilityMargin;
            steeperBoardsProbabilities.put(steeperBoard, new Pair<>(probabilityStart, probabilityEnd));
            probabilityStart = probabilityEnd;
        }

        double random = Math.random();

        return steeperBoardsProbabilities.keySet().stream().filter((steeperBoard) -> {
            Pair<Double, Double> probability = steeperBoardsProbabilities.get(steeperBoard);
            return random >= probability.getFirst() && random <= probability.getSecond();
        }).findFirst();
    }

    @Override
    public Output.Type goal(Board initialBoard) {
        Board currentBoard = new Board(initialBoard);
        int k = currentBoard.getK();

        int maxPlateauMoves = k * k, plateauMoves = 0;
        int minCrossings = FitnessScore.evaluate(currentBoard);

        Board[] currentBoardSuccessors = currentBoard.expand();

        while (true) {
            Optional<Board> maybePeekBoard = this.peek(currentBoardSuccessors);
            if (maybePeekBoard.isPresent()) {
                Board peekBoard = maybePeekBoard.get();
                System.out.println("Peek board: " + peekBoard);
                return Output.Type.GLOBAL_MAXIMA;
            }

            Optional<Board> maybeNextBoard = this.search(currentBoardSuccessors, currentBoard);
            if (maybeNextBoard.isEmpty()) {
                return Output.Type.LOCAL_MAXIMA;
            }

            Board nextBoard = maybeNextBoard.get();
            int nextCrossings = FitnessScore.evaluate(nextBoard);

            if (nextCrossings > minCrossings) {
                return Output.Type.LOCAL_MAXIMA;
            } else if (nextCrossings == minCrossings) {
                if (plateauMoves >= maxPlateauMoves) {
                    return Output.Type.PLATEAU;
                } else {
                    currentBoard = nextBoard;
                    currentBoardSuccessors = currentBoard.expand();
                    ++plateauMoves;
                }
            } else {
                currentBoard = nextBoard;
                currentBoardSuccessors = currentBoard.expand();
                minCrossings = nextCrossings;
                plateauMoves = 0;
            }
        }
    }

    public static void main(String[] args) {
        QueenStochasticHillClimbing solver = new QueenStochasticHillClimbing();

        Scanner scanner = new Scanner(Input.TEST_7x7);

        int k = scanner.nextInt();

        Output.printType(solver.goal(Population.generateOne(k)));
        Output.printReport(solver.report(k));
    }
}
