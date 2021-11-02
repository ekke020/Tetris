package manager;

public enum GameLoopState {

    GRAVITY, ROW_DELETE, GAME_OVER;

    private static GameLoopState loopState = GameLoopState.GRAVITY;

    public static GameLoopState getLoopState() {
        return loopState;
    }

    public static void setLoopState(GameLoopState loopState) {
        GameLoopState.loopState = loopState;
    }

}
