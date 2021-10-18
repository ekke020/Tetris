package manager;

import gui.Block;
import gui.frames.GameFrame;
import sound.AudioPlayer;

import javax.swing.*;
import java.util.List;

public class GameLoop extends SwingWorker<Boolean, Integer> {

    private static boolean running = true;
    private final GameFrame gameFrame;
    private final GameManager gameManager;
    private final double UPDATE_CAP = 1.0/60.0;
    private long totalFramesCount = 0;
    private long targetFrameCount = GameStats.getGameSpeed();

    public GameLoop(GameFrame gameFrame, GameManager gameManager) {
        this.gameFrame = gameFrame;
        this.gameManager = gameManager;
    }

    @Override
    protected Boolean doInBackground() {
        running = true;
        boolean render = false;
        boolean rowScan = false;
        boolean animate = false;
        List<Block> blocks = null;

        double firstTime = 0;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime = 0;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0; // Frames that has passed.
        int fps = 0; // Frames per seconds.
        gameManager.updateCurrentTetromino();
        while(running) {

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

                // TODO: FIX THIS PILE OF CODE
                if (rowScan) {
                    blocks = gameManager.scanRows();
                    if (blocks == null) {
                        gameManager.updateCurrentTetromino();
                        gameManager.setUpdate(true);
                    }
                    rowScan = false;
                }
                if (animate) {
                    gameManager.animateRows(blocks, !blocks.get(0).isHide());
                    targetFrameCount = 0;
                    gameManager.setUpdate(false);
//                    gameManager.updateCurrentTetromino();
//                    gameManager.setUpdate(true);
//                    AudioPlayer.play(AudioPlayer.LINE_CLEARED);
                }
                if (gameManager.isUpdate()) {
                    if (gameManager.collision()) {
                        rowScan = true;
                    } else {
                        gameManager.setUpdate(false);
                        targetFrameCount = totalFramesCount + GameStats.getGameSpeed();
                    }
                }
                if (frameTime >= 1.0) {
                    frameTime = 0;
                    fps = frames;
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
                if (totalFramesCount == targetFrameCount)
                    gameManager.setUpdate(true);
                if (blocks != null) {
                    animate = true;
                }
            }
        }
        return true;
    }
}
