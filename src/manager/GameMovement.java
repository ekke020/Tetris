package manager;

import gui.Block;
import sound.AudioPlayer;
import tetromino.Tetromino;


public class GameMovement {

    private final GameManager gameManager;
    private final Block[][] tetrisBlocks;
    private GameMovementListener gameMovementListener;

    public GameMovement(GameManager gameManager, Block[][] tetrisBlocks) {
        this.gameManager = gameManager;
        this.tetrisBlocks = tetrisBlocks;
    }

    public void moveLeft() {
        if (getCurrentTetromino() == null || GameLoop.isPaused()) {
            return;
        }
        if (isMoveLegal(-1)) {
            move(-1);
            AudioPlayer.play(AudioPlayer.MOVE);
        } else {
            AudioPlayer.play(AudioPlayer.MOVE_DENIED);
        }
    }

    public void moveRight() {
        if (getCurrentTetromino() == null || GameLoop.isPaused()) {
            return;
        }
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
        if (getCurrentTetromino() == null) {
            return;
        }
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
        if (getCurrentTetromino() == null) {
            return;
        }
        GameStats.toggleSoftDrop(on);
    }

    public void hardDrop() {
        //TODO: Increase score based on the rows dropped.
        if (getCurrentTetromino() == null || GameLoop.isPaused()) {
            return;
        }
        gameManager.setCurrentTetrominoInBlock(false);
        int row = getNewRowNumber();
        for (int[] coordinates : getCurrentTetrominoCoordinates()) {
            coordinates[0] += row;
        }
        getCurrentTetromino().setCoordinates(getCurrentTetrominoCoordinates());
        gameManager.setCurrentTetrominoInBlock(true);
        gameMovementListener.moveEventOccurred();
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

    public void setGameMovementListener(GameMovementListener listener) {
        this.gameMovementListener = listener;
    }
}
