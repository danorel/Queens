package hillClimbing;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import org.jetbrains.annotations.Nullable;

import hillClimbing.abstractions.QueenHillClimbingSolver;
import hillClimbing.entities.Board;
import hillClimbing.test.Input;
import hillClimbing.test.Output;
import hillClimbing.utils.FitnessScore;
import hillClimbing.utils.Population;

public class QueenFirstChoiceHillClimbing extends QueenHillClimbingSolver {

    @Override
    public Optional<Board> search(@Nullable Board[] successorBoards, Board currentBoard) {
        int currentCrossings = FitnessScore.evaluate(currentBoard);

        int k = currentBoard.getK();
        int maxRandomMoves = k * k, randomMoves = 0;

        while (true) {
            if (randomMoves >= maxRandomMoves) {
                return Optional.empty();
            }

            Board nextBoard = currentBoard.random();

            int nextCrossings = FitnessScore.evaluate(nextBoard);

            if (nextCrossings < currentCrossings) {
                return Optional.of(nextBoard);
            }

            ++randomMoves;
        }
    }

    @Override
    public Output.Type goal(Board initialBoard) {
        Board currentBoard = new Board(initialBoard);
        int k = initialBoard.getK();

        int maxPlateauMoves = k * k, plateauMoves = 0;
        int minCrossings = FitnessScore.evaluate(initialBoard);

        Board[] currentBoardSuccessors = currentBoard.expand();

        while (true) {
            Optional<Board> maybePeekBoard = this.peek(currentBoardSuccessors);
            if (maybePeekBoard.isPresent()) {
                Board peekBoard = maybePeekBoard.get();
                System.out.println("Peek board: " + peekBoard);
                return Output.Type.GLOBAL_MAXIMA;
            }

            Optional<Board> maybeNextBoard = this.search(null, currentBoard);
            if (maybeNextBoard.isEmpty()) {
                return Output.Type.PLATEAU;
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
        QueenFirstChoiceHillClimbing solver = new QueenFirstChoiceHillClimbing();

        Scanner scanner = new Scanner(Input.TEST_7x7);

        int k = scanner.nextInt();

        Output.printType(solver.goal(Population.generateOne(k)));
        Output.printReport(solver.report(k));
    }
}
