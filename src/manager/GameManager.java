package manager;

import gui.Block;
import player.Player;
import tetromino.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameManager implements ActionListener {

    private Tetromino currentTetromino;
    private final Player player;
    private final Block[][] tetrisBlocks;
    private boolean circumvention = true;

    private int delay;
    private final Timer timer;

    public Tetromino getCurrentTetromino() {
        return currentTetromino;
    }

    public int getDelay() {
        return delay;
    }

    public Timer getTimer() {
        return timer;
    }

    public GameManager(Block[][] tetrisBlocks, Player player) {
        this.tetrisBlocks = tetrisBlocks;
        this.player = player;
        delay = GameStats.getGameSpeed(player.getLevel());
        timer = new Timer(delay, this);
        currentTetromino = player.loadNewTetromino();
        player.updateFields();
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        collision();
        if (circumvention) {
            gravity();
        } else {
            currentTetromino = player.loadNewTetromino();
            scanRows();
            circumvention = true;
        }
    }

    private void collision() {
        int[][] currentTetrominoCoordinates = currentTetromino.getCoordinates();
        boolean stop = false;
        for (int[] coordinates : currentTetrominoCoordinates) {
            int rowCoordinate = coordinates[0];
            int colCoordinate = coordinates[1];
            if (rowCoordinate < 23) {
                Tetromino tetromino = tetrisBlocks[rowCoordinate + 1][colCoordinate].getTetromino();
                if (tetromino != currentTetromino && tetromino != null) {
                    stop = true;
                }
            }
            if (rowCoordinate == 23 || stop) {
                circumvention = false;
                break;
            }
        }
    }

    private void gravity() {
        setCurrentTetrominoInBlock(false);
        int[][] currentTetrominoCoordinates = currentTetromino.getCoordinates();
        for (int[] coordinates : currentTetrominoCoordinates) {
            coordinates[0]++;
        }
        currentTetromino.setCoordinates(currentTetrominoCoordinates);
        setCurrentTetrominoInBlock(true);
    }

    public void setCurrentTetrominoInBlock(boolean tetromino) {
        for (int[] coordinates : currentTetromino.getCoordinates()) {
            int rowCoordinate = coordinates[0];
            int colCoordinate = coordinates[1];
            if (tetromino) {
                tetrisBlocks[rowCoordinate][colCoordinate].setTetromino(currentTetromino);
            } else {
                tetrisBlocks[rowCoordinate][colCoordinate].setTetromino(null);
            }
        }
    }

    private void scanRows() {
        boolean delete;
        int lines = 0;
        int row = 0;
        for (Block[] blocks : tetrisBlocks) {
            delete = true;
            for (Block block : blocks) {
                Tetromino tetromino = block.getTetromino();
                if (tetromino == null) {
                    delete = false;
                    break;
                }
            }
            if (delete) {
                row = blocks[0].getBlockRow();
                removeRow(blocks);
                lines++;
            }
            if (lines > 0 && !delete) {
                break;
            }
        }
        if (lines > 0) {
            moveEverythingDown(row, lines);
        }
        updateElements(lines);
    }

    private void removeRow(Block[] block) {
        for (Block value : block) {
            value.setTetromino(null);
        }
    }

    private void moveEverythingDown(int rows, int lines) {
        for (int row = rows - lines; row > 0; row--) {
            for (int collum = 0; collum < 12; collum++) {
                Tetromino tetromino = tetrisBlocks[row][collum].getTetromino();
                tetrisBlocks[row][collum].setTetromino(null);
                tetrisBlocks[row + lines][collum].setTetromino(tetromino);
            }
        }
    }
    private void updateElements(int lines) {
        player.increaseScore(lines);
        player.updateFields();
        updateDelay();
    }

    private void updateDelay() {
        delay = GameStats.getGameSpeed(player.getLevel());
        timer.setDelay(delay);
    }

    public void updateCurrentTetromino() {
        currentTetromino = player.loadNewTetromino();
        scanRows();
    }
}
