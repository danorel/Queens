package hillClimbing.abstractions;

import java.util.HashMap;
import java.util.Map;

import hillClimbing.entities.Board;
import hillClimbing.test.Output;
import hillClimbing.utils.Population;

public interface QueenHillClimbingAnalyzer {
    Output.Type goal(Board initialBoard);

    default Map<Output.Type, Integer> report(int k) {
        HashMap<Output.Type, Integer> report = new HashMap<>();

        Board[] initialBoards = Population.generateAll(k);

        for (Board initialBoard : initialBoards) {
            Output.Type result = this.goal(initialBoard);
            if (report.containsKey(result)) {
                Integer frequency = report.get(result);
                report.put(result, frequency + 1);
            } else {
                report.put(result, 1);
            }
        }

        return report;
    }
}
