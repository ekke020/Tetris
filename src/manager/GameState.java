package manager;

public enum GameState {

    PAUSE, PLAY;
    private static GameState gameState = GameState.PLAY;
    private static String stateName;

    public static GameState getGameState() {
        return GameState.gameState;
    }

    public static String getStateName() {
        return stateName;
    }

    public static void changeState() {
        if (gameState == GameState.PLAY) {
            gameState = GameState.PAUSE;
            stateName = "Resume";
        } else {
            gameState = GameState.PLAY;
            stateName = "Pause";
        }
    }
}
