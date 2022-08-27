package hillClimbing.abstractions;

import hillClimbing.entities.Board;
import hillClimbing.test.Output;

public abstract class QueenHillClimbingSolver implements QueenHillClimbingAnalyzer {
    public abstract Output.Type goal(Board initialBoard);
}
