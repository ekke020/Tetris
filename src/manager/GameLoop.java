package manager;

import gui.frames.GameFrame;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameLoop extends SwingWorker<Boolean, Integer> {

    private static boolean running = true;
    private GameFrame gameFrame;
    private GameManager gameManager;
    private final double UPDATE_CAP = 1.0/60.0;
    private long totalFramesCount = 0;
    private int framesPerRow = 60;
    private long targetFrameCount = 60;
    private boolean update;

    public GameLoop(GameFrame gameFrame, GameManager gameManager) {
        this.gameFrame = gameFrame;
        this.gameManager = gameManager;
    }

    @Override
    protected Boolean doInBackground() {
        running = true;
        boolean render = false;
        double firstTime = 0;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime = 0;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0; // Frames that has passed.
        int fps = 0; // Frames per seconds.
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

                if (update) {
                    gameManager.update();
                    targetFrameCount += framesPerRow;
                    update = false;
                }
                if (frameTime >= 1.0) {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss");
                    System.out.println(LocalDateTime.now().format(formatter));
                    System.out.println("FPS: " + fps);
                    System.out.println("Total frames: " + totalFramesCount);
                }
            }
            if (render) {
                // Render game 60 times per second.
                gameFrame.repaint();
                frames++;
                totalFramesCount++;
                if (totalFramesCount == targetFrameCount)
                    update = true;
            }
        }
        return true;
    }
}
