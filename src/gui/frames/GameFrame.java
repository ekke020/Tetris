package gui.frames;

import gui.Block;
import manager.*;
import sound.AudioPlayer;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.util.ArrayList;
import java.util.List;
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
        addLetters();
    }

    private void addLetters() {
        String[] game = {" ", "G", "A", "M", "E", " "};
        String[] over = {" ", "O", "V", "E", "R", " "};
        String[] score = {"S", "C", "O", "R", "E", " "};
        String[] press = {"P", "R", "E", "S", "S", " "};
        String[] enter = {"E", "N", "T", "E", "R", " "};

        for (int i = 0; i < 6; i++) {
             TETRIS_BLOCKS[9][i + 3].setScoreNumber(game[i]);
             TETRIS_BLOCKS[10][i + 3].setScoreNumber(over[i]);
             TETRIS_BLOCKS[13][i + 3].setScoreNumber(score[i]);
             TETRIS_BLOCKS[17][i + 3].setScoreNumber(press[i]);
             TETRIS_BLOCKS[18][i + 3].setScoreNumber(enter[i]);
        }
    }
    private void addBorder() {
        Border raisedBevel = BorderFactory.createSoftBevelBorder(BevelBorder.RAISED, Color.GRAY, Color.GRAY);
        Border loweredBevel = BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED, Color.GRAY, Color.GRAY);

        Border compound = BorderFactory.createCompoundBorder(
                raisedBevel, loweredBevel);
        setBorder(compound);
    }

    public void showEndGameScreen(int score) {
        String points = String.valueOf(score);
        List<String> scoreIndex = new java.util.ArrayList<>(List.of(points.split("")));
        for (int i = 8; i > 2; i--) {
            TETRIS_BLOCKS[14][i].setTetromino(null);
            TETRIS_BLOCKS[14][i].setShowScoreNumber(true);
            if (!scoreIndex.isEmpty()) {
                String point = scoreIndex.get(scoreIndex.size() - 1);
                scoreIndex.remove(scoreIndex.size() - 1);
                TETRIS_BLOCKS[14][i].setScoreNumber(point);
            } else {
                TETRIS_BLOCKS[14][i].setScoreNumber(" ");
            }
        }
        for (int i = 3; i < 9; i++) {
            TETRIS_BLOCKS[9][i].setTetromino(null);
            TETRIS_BLOCKS[9][i].setShowScoreNumber(true);
            TETRIS_BLOCKS[10][i].setTetromino(null);
            TETRIS_BLOCKS[10][i].setShowScoreNumber(true);
            TETRIS_BLOCKS[13][i].setTetromino(null);
            TETRIS_BLOCKS[13][i].setShowScoreNumber(true);
            TETRIS_BLOCKS[17][i].setTetromino(null);
            TETRIS_BLOCKS[17][i].setShowScoreNumber(true);
            TETRIS_BLOCKS[18][i].setTetromino(null);
            TETRIS_BLOCKS[18][i].setShowScoreNumber(true);
        }
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
