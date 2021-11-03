package manager;

import sound.AudioPlayer;
import tetromino.Tetromino;
import static gui.frames.GameFrame.TETRIS_BLOCKS;
import static gui.frames.GameFrame.boardSize;
import static manager.GameManager.getCurrentTetromino;
import static manager.GameManager.setCurrentTetrominoInBlock;

public class GameMovement {

    private GameMovementListener gameMovementListener;

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
        for (int[] coordinates : getCurrentTetromino().getCoordinates()) {
            int rowCoordinate = coordinates[0];
            int colCoordinate = coordinates[1] + direction;
            if (colCoordinate < 0 || colCoordinate > 11) {
                return false;
            }
            Tetromino tetromino = TETRIS_BLOCKS[rowCoordinate][colCoordinate].getTetromino();
            if (tetromino != getCurrentTetromino() && tetromino != null) {
                return false;
            }
        }
        return true;
    }

    private void move(int direction) {
        setCurrentTetrominoInBlock(false);
        for (int[] coordinates : getCurrentTetromino().getCoordinates()) {
            coordinates[1] += direction;
        }
        getCurrentTetromino().setCoordinates(getCurrentTetromino().getCoordinates());
        setCurrentTetrominoInBlock(true);
    }

    public void spin() {
        if (getCurrentTetromino() == null) {
            return;
        }
        int[][] updatedCoordinates = getCurrentTetromino().getSpinCoordinates();

        if (isSpinLegal(updatedCoordinates)) {
            setCurrentTetrominoInBlock(false);
            getCurrentTetromino().setCoordinates(updatedCoordinates);
            setCurrentTetrominoInBlock(true);
            getCurrentTetromino().incrementSpinCounter();
            AudioPlayer.play(AudioPlayer.SPIN);
        }
    }

    private boolean isSpinLegal(int[][] updatedCoordinates) {
        for (int[] coordinates : updatedCoordinates) {
            int rowCoordinate = coordinates[0];
            int colCoordinate = coordinates[1];
            if (rowCoordinate < 0 || rowCoordinate > boardSize) {
                return false;
            }
            if (colCoordinate < 0 || colCoordinate > 11) {
                return false;
            }
            Tetromino tetromino = TETRIS_BLOCKS[rowCoordinate][colCoordinate].getTetromino();
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
        setCurrentTetrominoInBlock(false);
        int row = getNewRowNumber();
        for (int[] coordinates : getCurrentTetromino().getCoordinates()) {
            coordinates[0] += row;
        }
        getCurrentTetromino().setCoordinates(getCurrentTetromino().getCoordinates());
        setCurrentTetrominoInBlock(true);
        GameStats.setHardDropRows(row);
        gameMovementListener.moveEventOccurred();
    }

    private int getNewRowNumber() {
        int row = 1;
        int rowCoordinate = 0;
        while (true) {
            for (int[] coordinates : getCurrentTetromino().getCoordinates()) {
                rowCoordinate = coordinates[0] + row;
                int colCoordinate = coordinates[1];
                if (rowCoordinate > boardSize) {
                    row--;
                    break;
                }
                Tetromino tetromino = TETRIS_BLOCKS[rowCoordinate][colCoordinate].getTetromino();
                if (tetromino != getCurrentTetromino() && tetromino != null) {
                    return row - 1;
                }
            }
            if (rowCoordinate >= boardSize) {
                return row;
            }
            row++;
        }
    }

    public void setGameMovementListener(GameMovementListener listener) {
        this.gameMovementListener = listener;
    }
}
