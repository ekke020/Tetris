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
    private long targetAnimateFrame = -1;

    public GameLoop(GameFrame gameFrame, GameManager gameManager) {
        this.gameFrame = gameFrame;
        this.gameManager = gameManager;
    }

    @Override
    protected Boolean doInBackground() {
        running = true;
        boolean render = false;
        boolean animate = false;
        List<Block> blocks = null;

        double firstTime = 0;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime = 0;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0; // Frames that has passed.
        int fps = 0; // Frames per seconds.
        int animations = 0;
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
                if (animate) {
                    gameManager.animateRows(blocks, !blocks.get(0).isHide());
                    targetAnimateFrame = totalFramesCount + 5;
                    animate = false;
                    animations++;
                    if (animations > 3) {
                        gameManager.updateBoard(blocks);
                        blocks = null;
                        AudioPlayer.play(AudioPlayer.LINE_CLEARED);
                        targetAnimateFrame = -1;
                        targetFrameCount = totalFramesCount + 1;
                    }
                }
                if (gameManager.isUpdate()) {
                    if (gameManager.collision()) {
                        blocks = gameManager.scanRows();
                        gameManager.updateCurrentTetromino();
                        if (blocks != null) {
                            targetAnimateFrame = totalFramesCount + 1;
                        } else {
                            targetFrameCount = totalFramesCount + GameStats.getGameSpeed();
                        }
                    } else {
                        targetFrameCount = totalFramesCount + GameStats.getGameSpeed();
                    }
                    gameManager.setUpdate(false);
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
                if (totalFramesCount == targetFrameCount) {
                    gameManager.setUpdate(true);
                }
                if (totalFramesCount == targetAnimateFrame) {
                    animate = true;
                }
            }
        }
        return true;
    }
}
