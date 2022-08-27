package evolutionary.utils;

import evolutionary.entities.Board;

import java.util.Arrays;
import java.util.Optional;

public class Search {
    public static Optional<Board> uber(Board []population) {
        return Arrays.stream(population).filter(maybeUber -> FitnessScore.evaluate(maybeUber) == 0).findFirst();
    }
}
