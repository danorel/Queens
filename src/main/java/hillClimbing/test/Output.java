package hillClimbing.test;

public class Output {
    public enum OutputType {
        PLATEAU,
        LOCAL_MAXIMA,
        GLOBAL_MAXIMA,
        UNKNOWN,
    }

    public static void print(OutputType result) {
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
}
