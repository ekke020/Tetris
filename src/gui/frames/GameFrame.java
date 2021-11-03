package gui.frames;

import gui.Block;
import manager.*;
import sound.AudioPlayer;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;


public class GameFrame extends JPanel {

    public static final Block[][] TETRIS_BLOCKS = new Block[26][12];
    public static final int boardSize = TETRIS_BLOCKS.length - 1;

    public GameFrame(int width, int height) {
        setLayout(new GridLayout(24, 12));
        addBorder();
        setOpaque(false);
        setPreferredSize(new Dimension(width, height));
        createBoard();
    }

    private void createBoard() {

        for (int i = 0; i < TETRIS_BLOCKS.length; i++) {
            for (int j = 0; j < TETRIS_BLOCKS[i].length; j++) {
                TETRIS_BLOCKS[i][j] = new Block(i, j);
                if (i > 1)
                    add(TETRIS_BLOCKS[i][j]);
            }
        }
    }

    private void addBorder() {
        Border raisedBevel = BorderFactory.createSoftBevelBorder(BevelBorder.RAISED, Color.GRAY, Color.GRAY);
        Border loweredBevel = BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED, Color.GRAY, Color.GRAY);

        Border compound = BorderFactory.createCompoundBorder(
                raisedBevel, loweredBevel);
        setBorder(compound);
    }

    public void switchStates() {
        System.out.println("Width: " + TETRIS_BLOCKS[0][0].getWidth() + "\nHeight: " + TETRIS_BLOCKS[0][0].getHeight());
        switch (GameState.getGameState()) {
            case PAUSE -> {
                GameLoop.setPaused(true);
                AudioPlayer.play(AudioPlayer.PAUSE);
                AudioPlayer.pauseBackgroundMusic();
            }
            case PLAY -> {
                GameLoop.setPaused(false);
                AudioPlayer.resumeBackgroundMusic();
            }
        }
    }
}
