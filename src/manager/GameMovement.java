package manager;

import gui.Block;
import sound.AudioPlayer;
import tetromino.Tetromino;


public record GameMovement(GameManager gameManager, Block[][] tetrisBlocks) {

    public void moveLeft() {
        if (isMoveLegal(-1)) {
            move(-1);
            AudioPlayer.play(AudioPlayer.MOVE);
        } else {
            AudioPlayer.play(AudioPlayer.MOVE_DENIED);
        }
    }

    public void moveRight() {
        if (isMoveLegal(1)) {
            move(1);
            AudioPlayer.play(AudioPlayer.MOVE);
        } else {
            AudioPlayer.play(AudioPlayer.MOVE_DENIED);
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
            AudioPlayer.play(AudioPlayer.SPIN);
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
//            gameManager.getTimer().setDelay(delay / 2);
        } else {
//            gameManager.getTimer().setDelay(delay);
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
        AudioPlayer.play(AudioPlayer.TETROMINO_LANDING);
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
