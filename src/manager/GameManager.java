package manager;

import gui.Block;
import player.Player;
import sound.AudioPlayer;
import tetromino.*;

import java.util.*;

import static gui.frames.GameFrame.TETRIS_BLOCKS;
import static gui.frames.GameFrame.boardSize;


public class GameManager {

    private static Tetromino currentTetromino;
    private final Player player;
    private final List<Block[]> fullRows = new ArrayList<>();
    private boolean hide = false;
    private boolean gameOver = false;


    public static Tetromino getCurrentTetromino() {
        return currentTetromino;
    }

    public void setCurrentTetrominoNull() {
        currentTetromino = null;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public GameManager(Player player) {
        this.player = player;
        updateCurrentTetromino();
    }

    public void updateCurrentTetromino() {
        currentTetromino = player.loadNewTetromino();
        player.increaseScoreByDroppedRows();
        checkIfGameIsOver();
    }

    private void checkIfGameIsOver() {
        for (int[] coordinate : currentTetromino.getCoordinates()) {
            if (TETRIS_BLOCKS[coordinate[0] + 1][coordinate[1]].getTetromino() != null) {
                gameOver = true;
                break;
            }
        }
    }

    public boolean collision() {
        int[][] currentTetrominoCoordinates = currentTetromino.getCoordinates();
        boolean stop = false;
        for (int[] coordinates : currentTetrominoCoordinates) {
            int rowCoordinate = coordinates[0];
            int colCoordinate = coordinates[1];
            if (rowCoordinate < boardSize) {
                Tetromino tetromino = TETRIS_BLOCKS[rowCoordinate + 1][colCoordinate].getTetromino();
                if (tetromino != currentTetromino && tetromino != null) {
                    stop = true;
                }
            }
            if (rowCoordinate == boardSize || stop) {
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

    public static void setCurrentTetrominoInBlock(boolean tetromino) {
        for (int[] coordinates : currentTetromino.getCoordinates()) {
            int rowCoordinate = coordinates[0];
            int colCoordinate = coordinates[1];
            if (tetromino) {
                TETRIS_BLOCKS[rowCoordinate][colCoordinate].setTetromino(currentTetromino);
            } else {
                TETRIS_BLOCKS[rowCoordinate][colCoordinate].setTetromino(null);
            }
        }
    }

    public void animateRows() {
        for (Block[] fullRow : fullRows) {
            for (Block block : fullRow) {
                block.setHide(!hide);
            }
        }
        hide = !hide;
    }

    public boolean isAnyRowFull() {
        boolean delete;
        int lines = 0;
        for (Block[] blocks : TETRIS_BLOCKS) {
            delete = true;
            for (Block block : blocks) {
                Tetromino tetromino = block.getTetromino();
                if (tetromino == null) {
                    delete = false;
                    break;
                }
            }
            if (delete) {
                fullRows.add(blocks);
                lines++;
            }
            if (lines > 3) {
                break;
            }
        }
        if (lines > 0) {
            if (lines == 4) {
                AudioPlayer.play(AudioPlayer.TETRIS);
            } else {
                AudioPlayer.play(AudioPlayer.LINE_CLEARED);
            }
            return true;
        }
        return false;
    }

    public void moveEverythingDown() {
        HashMap<Integer, List<Block[]>> listMap = splitRows();
        for(Map.Entry<Integer, List<Block[]>> entry : listMap.entrySet()) {
            if (entry.getValue().isEmpty()) {
                updateElements(fullRows.size());
                fullRows.clear();
                return;
            }
            removeRows(entry.getValue());
            int bottomMostRow = entry.getValue().get(entry.getValue().size() - 1)[0].getBlockRow();
            int lines = entry.getValue().size();
            for (int row = bottomMostRow - lines; row > 0; row--) {
                for (int collum = 0; collum < 12; collum++) {
                    Tetromino tetromino = TETRIS_BLOCKS[row][collum].getTetromino();
                    TETRIS_BLOCKS[row][collum].setTetromino(null);
                    TETRIS_BLOCKS[row + lines][collum].setTetromino(tetromino);
                }
            }
        }
        updateElements(fullRows.size());
        fullRows.clear();
    }

    private HashMap<Integer, List<Block[]>> splitRows() {
        HashMap<Integer, List<Block[]>> listMap = new HashMap<>();
        List<Block[]> rowStackOne = new ArrayList<>();
        List<Block[]> rowStackTwo = new ArrayList<>();
        listMap.put(0, rowStackOne);
        listMap.put(1, rowStackTwo);

        for (Block[] fullRow : fullRows) {
            if (rowStackOne.isEmpty()) {
                rowStackOne.add(fullRow);
            } else if (fullRow[0].getBlockRow() - rowStackOne.get(rowStackOne.size() - 1)[0].getBlockRow() == 1) {
                rowStackOne.add(fullRow);
            } else {
                rowStackTwo.add(fullRow);
            }
        }
        return listMap;
    }

    private void updateElements(int lines) {
        player.increaseScoreByRowsRemoved(lines);
    }

    private void removeRows(List<Block[]> rows) {
        for (Block[] row : rows) {
            for (Block block : row) {
                block.setTetromino(null);
            }
        }
    }

    public void setEndOfGameShape(int[] coordinates) {
        TETRIS_BLOCKS[coordinates[0]][coordinates[1]].setTetromino(new EndOfGameShape());
    }
}
