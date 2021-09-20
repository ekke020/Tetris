package player;

import tetromino.Tetromino;


public class FieldUpdater {
    private final Tetromino tetromino;
    private final int level;
    private final int score;
    private final int lines;

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public int getLines() {
        return lines;
    }

    public Tetromino getTetromino() {
        return tetromino;
    }

    public FieldUpdater(Tetromino tetromino, int level, int score, int lines) {
        this.tetromino = tetromino;
        this.level = level;
        this.score = score;
        this.lines = lines;

    }
}
