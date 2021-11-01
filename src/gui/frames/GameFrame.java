package gui.frames;

import colors.Colors;
import gui.Block;
import manager.*;
import sound.AudioPlayer;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;


public class GameFrame extends JPanel {

    private Block[][] tetrisBlocks;

    public Block[][] getTetrisBlocks() {
        return tetrisBlocks;
    }

    public GameFrame(int width, int height) {
        setLayout(new GridLayout(24, 12));
        addBorder();
        setBackground(Colors.BACKGROUND_COLOR);
        setPreferredSize(new Dimension(width, height));
        createBoard();
    }

    private void createBoard() {
        tetrisBlocks = new Block[24][12];

        for (int i = 0; i < tetrisBlocks.length; i++) {
            for (int j = 0; j < tetrisBlocks[i].length; j++) {
                tetrisBlocks[i][j] = new Block(i, j);
                add(tetrisBlocks[i][j]);
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
        System.out.println("Width: " + tetrisBlocks[0][0].getWidth() + "\nHeight: " + tetrisBlocks[0][0].getHeight());
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
