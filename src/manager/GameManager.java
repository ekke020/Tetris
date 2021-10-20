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
    private boolean update = false;

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public Tetromino getCurrentTetromino() {
        return currentTetromino;
    }

    public GameManager(Block[][] tetrisBlocks, Player player) {
        this.tetrisBlocks = tetrisBlocks;
        this.player = player;
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

    public List<Block> scanRows() {
        boolean delete;
        int lines = 0;
        List<Block> fullRows = new ArrayList<>();
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
            return fullRows;
        }
        return null;
    }

    public void animateRows(List<Block> blocks, boolean hide) {
        for (Block block : blocks) {
            block.setHide(hide);
        }
    }

    public void updateBoard(List<Block> blocks) {
        removeRows(blocks);
        moveEverythingDown(blocks.get(blocks.size() - 1).getBlockRow(), blocks.size() / 12 );
        updateElements(blocks.size() / 12);
    }
    private void removeRows(List<Block> blocks) {
        for (Block block : blocks) {
            block.setTetromino(null);
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
    }

}
