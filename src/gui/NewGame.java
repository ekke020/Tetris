package gui;

import gui.frames.GameFrame;
import gui.menu.game.GameMenu;
import keybinds.KeyBinder;
import keybinds.KeybindingLoader;
import manager.GameLoop;
import manager.GameManager;
import manager.GameMovement;
import manager.GameState;
import player.Player;

import javax.swing.*;
import java.awt.*;

public class NewGame extends JPanel {

    private final GridBagConstraints gc;

    private GameMenu gameMenu;
    private GameFrame gameFrame;
    private Player player;
    private GameManager gameManager;

    public NewGame(int width, int height) {
        setLayout(new GridBagLayout());
        gc = new GridBagConstraints();
        addMenu((int) (width * 0.38), height);
        addNewPlayer();
        addNewGame((int) (width * 0.62), height);
        addGameManager();
        addKeyBindings();
        startGame();
        addComponentListener(new ComponentResizeListener() {
            @Override
            public void resizeTimedOut() {
                setComponentSizes();
            }
        });
    }

    private void addNewPlayer() {
        player = new Player();
        player.setPlayerListener(e ->
                gameMenu.updateMenu(e.getTetromino(), e.getLevel(), e.getScore(), e.getLines())
        );
    }

    private void addNewGame(int width, int height) {
        gameFrame = new GameFrame(width, height);
        gc.gridx = 1;
        gc.gridy = 0;

        gc.weightx = 1;
        gc.weighty = 0;

        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.fill = GridBagConstraints.NONE;
        add(gameFrame, gc);
    }

    private void addGameManager() {
        gameManager = new GameManager(gameFrame.getTetrisBlocks(), player);
    }

    private void addMenu(int width, int height) {
        gameMenu = new GameMenu(width, height);
        gameMenu.setMenuListener(e -> {
            switch (e.getId()) {
                case 0 -> {
                    GameState.changeState();
                    gameFrame.switchStates();
                    e.getMenuButton().setText(GameState.getStateName());
                }
                case 1 -> System.exit(0);
            }
        });

        gc.gridx = 0;
        gc.gridy = 0;

        gc.weightx = 0;
        gc.weighty = 0.1;

        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.fill = GridBagConstraints.NONE;

        add(gameMenu, gc);
    }

    private void startGame() {
        GameLoop gameLoop = new GameLoop(gameFrame, gameManager);
        gameLoop.execute();
    }

    private void addKeyBindings() {
        GameMovement gameMovement = new GameMovement(gameManager, gameFrame.getTetrisBlocks());
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
                "hardDrop", true, (evt) -> gameMovement.hardDrop());
    }

    private void setComponentSizes() {
        gameFrame.setPreferredSize(new Dimension((int) (getWidth()  * 0.62), getHeight()));
        gameMenu.setPreferredSize(new Dimension((int) (getWidth() * 0.38), getHeight()));
        SwingUtilities.updateComponentTreeUI(this);
    }
}
