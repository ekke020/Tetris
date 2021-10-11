package gui.frames;

import colors.Colors;
import gui.Block;
import keybinds.KeyBinder;
import keybinds.KeybindingLoader;
import manager.*;
import player.Player;
import sound.AudioPlayer;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;

import static sound.SoundPaths.*;

public class GameFrame extends JPanel {

    private final GameManager gameManager;
    private Block[][] tetrisBlocks;
    private final AudioPlayer ap;

    public GameFrame(Player player, int width, int height) {
        setLayout(new GridLayout(24, 12));
        addBorder();
        setBackground(Colors.BACKGROUND_COLOR);
        setPreferredSize(new Dimension(width, height));
        createBoard();

        Timer updateTimer = new Timer(10, e -> repaint());
        updateTimer.start();

        gameManager = new GameManager(tetrisBlocks, player);
        addKeyBindings();
        ap = new AudioPlayer(TETRIS_GAME_SOUND.getPath(), true, TETRIS_GAME_SOUND.getVolume());
        ap.play();
    }

    private void createBoard() {
        tetrisBlocks = new Block[24][12];

        for (int i = 0; i < tetrisBlocks.length; i++) {
            for (int j = 0; j < tetrisBlocks[i].length; j++) {
                tetrisBlocks[i][j] = new Block(i, j);
                add(tetrisBlocks[i][j]);
            }
        }
    }

    private void addBorder() {
        Border raisedBevel = BorderFactory.createSoftBevelBorder(BevelBorder.RAISED, Color.GRAY, Color.GRAY);
        Border loweredBevel = BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED, Color.GRAY, Color.GRAY);

        Border compound = BorderFactory.createCompoundBorder(
                raisedBevel, loweredBevel);
        setBorder(compound);
    }

    private void addKeyBindings() {
        GameMovement gameMovement = new GameMovement(gameManager, tetrisBlocks);

        KeyBinder.addKeyBinding(this, KeybindingLoader.getKeybinding("MOVE_LEFT"),
                "left", true, (evt) -> gameMovement.moveLeft());

        KeyBinder.addKeyBinding(this, KeybindingLoader.getKeybinding("MOVE_RIGHT"),
                "right", true, (evt) -> gameMovement.moveRight());

        KeyBinder.addKeyBinding(this, KeybindingLoader.getKeybinding("SPIN"),
                "spin", true, (evt) -> gameMovement.spin());

        KeyBinder.addKeyBinding(this, KeybindingLoader.getKeybinding("SOFT_DROP"),
                "softDrop", false, (evt) -> gameMovement.softDrop(true));

        KeyBinder.addKeyBinding(this, KeybindingLoader.getKeybinding("SOFT_DROP"),
                "softDropRelease", true, (evt) -> gameMovement.softDrop(false));

        KeyBinder.addKeyBinding(this,KeybindingLoader.getKeybinding("HARD_DROP"),
                "hardDrop", false, (evt) -> gameMovement.hardDrop());
    }

    public void switchStates() {
        System.out.println("Width: " + tetrisBlocks[0][0].getWidth() + "\nHeight: " + tetrisBlocks[0][0].getHeight());
        switch (GameState.getGameState()) {
            case PAUSE -> {
                gameManager.getTimer().stop();
                new AudioPlayer(PAUSE.getPath(), false, PAUSE.getVolume()).play();
                ap.pause();
            }
            case PLAY -> {
                gameManager.getTimer().start();
                ap.start();
            }
        }
    }
}
