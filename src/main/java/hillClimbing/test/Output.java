package hillClimbing.test;

import java.util.Map;

public class Output {
    public enum Type {
        PLATEAU,
        LOCAL_MAXIMA,
        GLOBAL_MAXIMA,
        UNKNOWN,
    }

    public static void printType(Type result) {
        switch (result) {
            case PLATEAU: {
                System.out.println("Instance has reached plateau");
                break;
            }
            case LOCAL_MAXIMA: {
                System.out.println("Instance has reached local maxima");
                break;
            }
            case GLOBAL_MAXIMA: {
                System.out.println("Instance has reached global maxima by " + result + " step");
                break;
            }
            default: {
                throw new RuntimeException("Unknown case");
            }
        }
    }

    public static void printReport(Map<Type, Integer> report) {
        report.forEach((type, frequency) -> System.out.println(type + ": " + frequency));
    }
}
