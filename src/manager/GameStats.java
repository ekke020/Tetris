package manager;

public class GameStats {


    public static int calculateLineScore(int level, int lines) {
        return switch (lines) {
            case 1 -> 40 * (level + 1);
            case 2 -> 100 * (level + 1);
            case 3 -> 300 * (level + 1);
            case 4 -> 1200 * (level + 1);
            default -> 0;
        };
    }

    public static boolean levelUp(int level, int lines) {
        int linesRequired = (level + 1) * 5;
        return lines >= linesRequired;
    }

    public static int getGameSpeed(int level) {
        return switch (level) {
            case 0 -> 1000;
            case 1 -> 900;
            case 2 -> 800;
            case 3 -> 700;
            case 4 -> 600;
            case 5 -> 500;
            case 6 -> 400;
            case 7 -> 300;
            case 8 -> 200;
            case 9 -> 100;
            default -> 0;
        };
    }
}
