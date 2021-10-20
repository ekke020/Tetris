package manager;

import gui.Block;
import player.Player;
import sound.AudioPlayer;
import tetromino.*;

import java.util.*;


public class GameManager {

    private Tetromino currentTetromino;
    private final Player player;
    private final Block[][] tetrisBlocks;

    public Tetromino getCurrentTetromino() {
        return currentTetromino;
    }
    public void setCurrentTetrominoNull() {
        currentTetromino = null;
    }
    public GameManager(Block[][] tetrisBlocks, Player player) {
        this.tetrisBlocks = tetrisBlocks;
        this.player = player;
        updateCurrentTetromino();
    }

    public void updateCurrentTetromino() {
        currentTetromino = player.loadNewTetromino();
    }

    public boolean collision() {
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
                AudioPlayer.play(AudioPlayer.TETROMINO_LANDING);
                return true;
            }
        }
        gravity();
        return false;
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
    private final List<Block> fullRows = new ArrayList<>();
    public boolean checkRows() {
        return scanRows() != null;
    }

    public void animateRows(boolean hide) {
        for (Block block : fullRows) {
            block.setHide(hide);
        }
    }

    private List<Block> scanRows() {
        boolean delete;
        int lines = 0;
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
                fullRows.addAll(List.of(blocks));
                lines++;
            }
            if (lines > 0 && !delete) {
                break;
            }
        }
        if (lines > 0) {
            if (lines == 4) {
                AudioPlayer.play(AudioPlayer.TETRIS);
            } else {
                AudioPlayer.play(AudioPlayer.LINE_CLEARED);
            }
            return fullRows;
        }
        return null;
    }

    public void moveEverythingDown() {
        removeRows(fullRows);
        int rows = fullRows.get(fullRows.size() - 1).getBlockRow();
        int lines = fullRows.size() / 12;
        for (int row = rows - lines; row > 0; row--) {
            for (int collum = 0; collum < 12; collum++) {
                Tetromino tetromino = tetrisBlocks[row][collum].getTetromino();
                tetrisBlocks[row][collum].setTetromino(null);
                tetrisBlocks[row + lines][collum].setTetromino(tetromino);
            }
        }
        fullRows.clear();
        updateElements(lines);
    }

    private void removeRows(List<Block> blocks) {
        for (Block block : blocks) {
            block.setTetromino(null);
        }
    }
    private void updateElements(int lines) {
        player.increaseScore(lines);
    }
}
