package hillClimbing;

import hillClimbing.abstractions.QueenHillClimbingSolver;
import hillClimbing.entities.Board;
import hillClimbing.test.Input;
import hillClimbing.test.Output;
import hillClimbing.utils.FitnessScore;
import hillClimbing.utils.Population;

import java.util.*;

public class QueenHillClimbing extends QueenHillClimbingSolver {

    @Override
    public Output.Type goal(Board initialBoard) {
        Board currentBoard = new Board(initialBoard);
        int k = initialBoard.getK();

        int maxPlateauMoves = k * k, plateauMoves = 0;
        int minCrossings = FitnessScore.evaluate(initialBoard);

        Board[] currentBoardSuccessors = currentBoard.expand();

        while (true) {
            Optional<Board> maybePeekBoard = Population.peek(currentBoardSuccessors);
            if (maybePeekBoard.isPresent()) {
                Board peekBoard = maybePeekBoard.get();
                System.out.println("Peek board: " + peekBoard);
                return Output.Type.GLOBAL_MAXIMA;
            }

            Optional<Board> maybeNextBoard = Population.min(currentBoardSuccessors);
            if (maybeNextBoard.isEmpty()) {
                return Output.Type.UNKNOWN;
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
        QueenHillClimbing solver = new QueenHillClimbing();

        Scanner scanner = new Scanner(Input.TEST_7x7);

        int k = scanner.nextInt();

        Output.printType(solver.goal(Population.generateOne(k)));
        Output.printReport(solver.report(k));
    }
}
