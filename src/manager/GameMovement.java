package manager;

import gui.Block;
import sound.AudioPlayer;
import sound.SoundPaths;
import tetromino.Tetromino;

import static sound.SoundPaths.*;

public record GameMovement(GameManager gameManager, Block[][] tetrisBlocks) {

    public void moveLeft() {
        if (isMoveLegal(-1)) {
            move(-1);
            new AudioPlayer(TETROMINO_MOVE.getPath(), false, TETROMINO_MOVE.getVolume()).play();
        } else {
            new AudioPlayer(TETROMINO_MOVE_DENIED.getPath(), false, TETROMINO_MOVE_DENIED.getVolume()).play();
        }
    }

    public void moveRight() {
        if (isMoveLegal(1)) {
            move(1);
            new AudioPlayer(TETROMINO_MOVE.getPath(), false, TETROMINO_MOVE.getVolume()).play();
        } else {
            new AudioPlayer(TETROMINO_MOVE_DENIED.getPath(), false, TETROMINO_MOVE_DENIED.getVolume()).play();
        }
    }

    private boolean isMoveLegal(int direction) {
        for (int[] coordinates : getCurrentTetrominoCoordinates()) {
            int rowCoordinate = coordinates[0];
            int colCoordinate = coordinates[1] + direction;
            if (colCoordinate < 0 || colCoordinate > 11) {
                return false;
            }
            Tetromino tetromino = tetrisBlocks[rowCoordinate][colCoordinate].getTetromino();
            if (tetromino != getCurrentTetromino() && tetromino != null) {
                return false;
            }
        }
        return true;
    }

    private void move(int direction) {
        gameManager.setCurrentTetrominoInBlock(false);
        for (int[] coordinates : getCurrentTetrominoCoordinates()) {
            coordinates[1] += direction;
        }
        getCurrentTetromino().setCoordinates(getCurrentTetrominoCoordinates());
        gameManager.setCurrentTetrominoInBlock(true);
    }

    public void spin() {
        int[][] updatedCoordinates = getCurrentTetromino().getSpinCoordinates();

        if (isSpinLegal(updatedCoordinates)) {
            gameManager.setCurrentTetrominoInBlock(false);
            getCurrentTetromino().setCoordinates(updatedCoordinates);
            gameManager.setCurrentTetrominoInBlock(true);
            getCurrentTetromino().incrementSpinCounter();
            new AudioPlayer(TETROMINO_SPIN.getPath(), false, TETROMINO_SPIN.getVolume()).play();
        }
    }

    private boolean isSpinLegal(int[][] updatedCoordinates) {
        for (int[] coordinates : updatedCoordinates) {
            int rowCoordinate = coordinates[0];
            int colCoordinate = coordinates[1];
            if (rowCoordinate < 0 || rowCoordinate > 23) {
                return false;
            }
            if (colCoordinate < 0 || colCoordinate > 11) {
                return false;
            }
            Tetromino tetromino = tetrisBlocks[rowCoordinate][colCoordinate].getTetromino();
            if (tetromino != getCurrentTetromino() && tetromino != null) {
                return false;
            }
        }
        return true;
    }

    public void softDrop(boolean on) {
        int delay = gameManager.getDelay();
        if (on) {
            gameManager.getTimer().setDelay(delay / 2);
        } else {
            gameManager.getTimer().setDelay(delay);
        }
    }

    public void hardDrop() {
        gameManager.setCurrentTetrominoInBlock(false);
        int row = getNewRowNumber();
        for (int[] coordinates : getCurrentTetrominoCoordinates()) {
            coordinates[0] += row;
        }
        getCurrentTetromino().setCoordinates(getCurrentTetrominoCoordinates());
        gameManager.setCurrentTetrominoInBlock(true);
        gameManager.updateCurrentTetromino();
        new AudioPlayer(TETROMINO_LANDING.getPath(),false, TETROMINO_LANDING.getVolume()).play();
    }

    private int getNewRowNumber() {
        int row = 1;
        int rowCoordinate = 0;
        while (true) {
            for (int[] coordinates : getCurrentTetrominoCoordinates()) {
                rowCoordinate = coordinates[0] + row;
                int colCoordinate = coordinates[1];
                if (rowCoordinate > 23) {
                    row--;
                    break;
                }
                Tetromino tetromino = tetrisBlocks[rowCoordinate][colCoordinate].getTetromino();
                if (tetromino != getCurrentTetromino() && tetromino != null) {
                    return row - 1;
                }
            }
            if (rowCoordinate >= 23) {
                return row;
            }
            row++;
        }
    }

    private Tetromino getCurrentTetromino() {
        return gameManager.getCurrentTetromino();
    }

    private int[][] getCurrentTetrominoCoordinates() {
        return getCurrentTetromino().getCoordinates();
    }
}
