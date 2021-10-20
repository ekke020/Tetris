package player;

import manager.GameStats;
import sound.AudioPlayer;
import tetromino.Tetromino;

import java.util.ArrayList;
import java.util.Collections;

public class Player {

    private int level = 0;
    private int score = 0;
    private int lines = 0;
    private ArrayList<Tetromino> activeTetrominos;
    private PlayerListener playerListener;

    public Player() {
        reloadTetrominos();
        GameStats.setGameSpeed(level);
    }

    public Tetromino loadNewTetromino() {
        Tetromino currentTetromino = activeTetrominos.get(0);
        activeTetrominos.remove(0);
        if (activeTetrominos.isEmpty()) {
            reloadTetrominos();
        }
        updateFields();
        return currentTetromino;
    }

    private void reloadTetrominos() {
        activeTetrominos = Tetromino.reloadTetrominos();
        Collections.shuffle(activeTetrominos);
    }

    public void increaseScore(int lines) {
        score += GameStats.calculateLineScore(level, lines);
        this.lines += lines;
        checkIfLevelUp();
        updateFields();
    }

    private void checkIfLevelUp() {
        boolean levelUp = GameStats.levelUp(level, lines);
        if (levelUp) {
            level++;
            GameStats.setGameSpeed(level);
            AudioPlayer.play(AudioPlayer.LEVEL_UP);
        }
    }

    private void updateFields() {
        FieldUpdater update = new FieldUpdater(getShowcase(), level, score, lines);
        playerListener.formEventOccurred(update);
    }

    private Tetromino getShowcase() {
        return activeTetrominos.get(0);
    }

    public void setPlayerListener(PlayerListener listener) {
        this.playerListener = listener;
    }
}
