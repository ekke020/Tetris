package manager;

import gui.frames.GameFrame;

import javax.swing.*;

public class GameLoop extends SwingWorker<Boolean, Integer> {

    private static boolean running = true;
    private static boolean paused = false;
    private final GameFrame gameFrame;
    private final GameManager gameManager;
    private final double UPDATE_CAP = 1.0/60.0;
    private long totalFramesCount = 0;
    private long targetFrameCount = GameStats.getGameSpeed();
    private long targetAnimateFrame = -1;
    private int rowAnimations = 0;

    public static void setPaused(boolean paused) {
        GameLoop.paused = paused;
    }

    public static boolean isPaused() {
        return paused;
    }

    public GameLoop(GameFrame gameFrame, GameManager gameManager) {
        this.gameFrame = gameFrame;
        this.gameManager = gameManager;
    }

    public void requestUpdate() {
        if (GameLoopState.getLoopState() != GameLoopState.ROW_DELETE)
            GameLoopState.setLoopState(GameLoopState.GRAVITY);
    }

    @Override
    protected Boolean doInBackground() {
        running = true;
        boolean render;

        double firstTime;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0; // Frames that has passed.
        int fps = 0; // Frames per seconds.
        while(running) {
            while (!paused) {
                render = false;
                // We divide our nanoseconds by one billion to make them into milliseconds. We take nanoTime to make it as accurate as possible
                firstTime = System.nanoTime() / 1000000000.0;
                passedTime = firstTime - lastTime;
                lastTime = firstTime;

                unprocessedTime += passedTime;
                frameTime += passedTime;
                while(unprocessedTime >= UPDATE_CAP) {
                    unprocessedTime -= UPDATE_CAP;
                    render = true;

                    switch (GameLoopState.getLoopState()) {
                        case GRAVITY -> callForGravity();
                        case ROW_DELETE -> deleteRows();
                        case WAITING -> isGameOver();
                    }

                    if (frameTime >= 1.0) {
                        frameTime = 0;
//                    fps = frames;
                        frames = 0;

//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss");
//                    System.out.println(LocalDateTime.now().format(formatter));
//                    System.out.println("FPS: " + fps);
//                    System.out.println("Total frames: " + totalFramesCount);
                    }
                }
                if (render) {
                    // Render game 60 times per second.
                    gameFrame.repaint();
                    frames++;
                    totalFramesCount++;
                    if (totalFramesCount == targetFrameCount) {
                        GameLoopState.setLoopState(GameLoopState.GRAVITY);
                    } else if (totalFramesCount == targetAnimateFrame) {
                        rowAnimations++;
                        GameLoopState.setLoopState(GameLoopState.ROW_DELETE);
                    }
                }
            }
        }
        return true;
    }

    private void callForGravity() {
        if (gameManager.collision()) {
            gameManager.setCurrentTetrominoNull();
            if(gameManager.isAnyRowFull()) {
                GameLoopState.setLoopState(GameLoopState.ROW_DELETE);
                rowAnimations = 0;
                targetFrameCount = -1;
            } else {
                gameManager.updateCurrentTetromino();
                targetFrameCount = totalFramesCount + 1;
            }
        } else {
            targetFrameCount = totalFramesCount + GameStats.getGameSpeed();
            GameLoopState.setLoopState(GameLoopState.WAITING);
        }
    }

    private void deleteRows() {
        gameManager.animateRows();
        if (rowAnimations == 5) {
            gameManager.updateCurrentTetromino();
            GameLoopState.setLoopState(GameLoopState.GRAVITY);
            gameManager.moveEverythingDown();
        } else {
            targetAnimateFrame = totalFramesCount + 20;
            GameLoopState.setLoopState(GameLoopState.WAITING);
        }
    }

    private void isGameOver() {

    }
}
