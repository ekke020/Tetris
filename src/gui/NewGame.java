package gui;

import sound.AudioPlayer;
import staticAssets.Colors;
import gui.frames.GameFrame;
import gui.menu.game.GameMenu;
import keybinds.KeyBinder;
import keybinds.KeybindingLoader;
import manager.*;
import player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class NewGame extends JPanel {

    private final GridBagConstraints gc;
    private EnterListener enterListener;

    private GameMenu gameMenu;
    private GameFrame gameFrame;
    private Player player;
    private GameManager gameManager;
    private GameLoop gameLoop;

    public NewGame(int width, int height, int level) {
        setLayout(new GridBagLayout());
        gc = new GridBagConstraints();
        setBackground(Colors.BACKGROUND_COLOR);

        addMenu((int) (width * 0.38), height);
        addNewPlayer(level);
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

    private void addMenu(int width, int height) {
        gameMenu = new GameMenu(width, height);
        gameMenu.setMenuListener(e -> {
            switch (e.getId()) {
                case 0 -> {
                    if (GameLoopState.getLoopState() == GameLoopState.GAME_OVER) {
                        return;
                    }
                    GameState.changeState();
                    gameFrame.switchStates();
                    e.getMenuButton().setText(GameState.getStateName());
                }
                case 1 -> System.exit(0);
            }
        });
        gc.gridx = 0;
        add(gameMenu, gc);
    }

    private void addNewPlayer(int level) {
        player = new Player(level);
        player.setPlayerListener(e -> {
            gameMenu.updateMenu(e.getTetromino(), e.getLevel(), e.getScore(), e.getLines());
            gameMenu.repaint();
        });
    }

    private void addNewGame(int width, int height) {
        gameFrame = new GameFrame(width, height);
        gc.gridx = 1;
        add(gameFrame, gc);
    }

    private void addGameManager() {
        gameManager = new GameManager(player);
    }

    private void startGame() {
        gameLoop = new GameLoop(gameFrame, gameManager);
        gameLoop.execute();
        gameLoop.setGameOverEventListener(() -> {
            gameFrame.showEndGameScreen(player.getScore());
            gameMenu.removeAll();
            gameMenu.repaint();
            gameMenu.revalidate();
            KeyBinder.addKeyBinding(this, KeyEvent.VK_ENTER, "ENTER", true, e -> {
                enterListener.enterEventOccurred();
            });
        });
        AudioPlayer.play(AudioPlayer.GAME);
    }

    private void addKeyBindings() {
        GameMovement gameMovement = new GameMovement();
        gameMovement.setGameMovementListener(() -> gameLoop.requestUpdate());

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

    public void setEnterListener(EnterListener listener) {
        this.enterListener = listener;
    }

}