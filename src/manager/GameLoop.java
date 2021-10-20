package manager;

import gui.Block;
import gui.frames.GameFrame;
import sound.AudioPlayer;

import javax.swing.*;

public class GameLoop extends SwingWorker<Boolean, Integer> {

    private static boolean running = true;
    private final GameFrame gameFrame;
    private final GameManager gameManager;
    private final double UPDATE_CAP = 1.0/60.0;
    private long totalFramesCount = 0;
    private long targetFrameCount = GameStats.getGameSpeed();
    private long targetAnimateFrame = -1;
    private boolean gameUpdate = true;
    private boolean removeRows = false;

    public GameLoop(GameFrame gameFrame, GameManager gameManager) {
        this.gameFrame = gameFrame;
        this.gameManager = gameManager;
    }

    public void requestUpdate() {
        if (!removeRows)
            gameUpdate = true;
    }

    @Override
    protected Boolean doInBackground() {
        running = true;
        boolean render = false;
        boolean hide = true;

        double firstTime = 0;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime = 0;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0; // Frames that has passed.
        int fps = 0; // Frames per seconds.
        while(running) {
            // TODO: Refactor this pile! (It works!) (fps is a borrowed int)
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

                if (gameUpdate) {
                    if (gameManager.collision()) {
                        gameManager.setCurrentTetrominoNull();
                       if(gameManager.checkRows()) {
                           removeRows = true;
                           fps = 0;
                           targetFrameCount = -1;
                       } else {
                           gameManager.updateCurrentTetromino();
                           targetFrameCount = totalFramesCount + 1;
                       }
                    } else {
                        targetFrameCount = totalFramesCount + GameStats.getGameSpeed();
                    }
                    gameUpdate = false;
                }
                if (removeRows) {
                    gameManager.animateRows(hide);
                    hide = !hide;
                    if (fps == 5) {
                        gameManager.updateCurrentTetromino();
                        gameUpdate = true;
                        gameManager.moveEverythingDown();
                    } else {
                        targetAnimateFrame = totalFramesCount + 20;
                    }
                    removeRows = false;
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
                    System.out.println("setting gameUpdate to true!");
                    gameUpdate = true;
                } else if (totalFramesCount == targetAnimateFrame) {
                    fps++;
                    removeRows = true;
                }
            }
        }
        return true;
    }
}
