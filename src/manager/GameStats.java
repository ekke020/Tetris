package manager;

public class GameStats {

    private static int gameSpeed;
    private static int softDrop = 1;
    private static int hardDropRows = 0;

    public static int getGameSpeed() {
        return gameSpeed / softDrop;
    }

    public static int getHardDropRows() {
        return hardDropRows;
    }

    public static void setHardDropRows(int hardDropRows) {
        GameStats.hardDropRows = hardDropRows;
    }

    public static void toggleSoftDrop(boolean on) {
        if (on) {
            softDrop = 3;
        } else {
            softDrop = 1;
        }
    }
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

    public static void setGameSpeed(int level) {
        switch (level) {
            case 0 -> gameSpeed = 60;
            case 1 -> gameSpeed = 53;
            case 2 -> gameSpeed = 49;
            case 3 -> gameSpeed = 45;
            case 4 -> gameSpeed = 41;
            case 5 -> gameSpeed = 37;
            case 6 -> gameSpeed = 33;
            case 7 -> gameSpeed = 28;
            case 8 -> gameSpeed = 22;
            case 9 -> gameSpeed = 17;
            default -> gameSpeed = 12;
        };
    }
}
