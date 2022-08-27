package hillClimbing.abstractions;

import java.util.Arrays;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import hillClimbing.entities.Board;
import hillClimbing.utils.FitnessScore;

public interface QueenHillClimbingSearcher {
    default Optional<Board> peek(Board[] successorBoards) {
        return Arrays.stream(successorBoards).filter(successorBoard -> FitnessScore.evaluate(successorBoard) == 0).findFirst();
    }

    Optional<Board> search(Board[] successorBoards, @Nullable Board boundaryBoard);
}
