package gui;

import gui.menu.MainMenu;
import keybinds.KeybindingLoader;
import gui.menu.GameMenu;
import manager.GameState;
import player.Player;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private final GridBagConstraints gc;

    private MainMenu mainMenu;
    private GameMenu gameMenu;
    private GameFrame gameFrame;
    private Player player;

    public Window() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        setPreferredSize(new Dimension(800, 1000));

        KeybindingLoader.loadKeybindings();
        setLayout(new GridBagLayout());
        gc = new GridBagConstraints();
        addMenu();
        addNewPlayer();
        addNewGame();
        pack();
        setLocationRelativeTo(null);

        setVisible(true);
    }
    private void addMainMenu() {
        mainMenu = new MainMenu();

        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 0.1;

        add(mainMenu, gc);
    }
    private void addNewPlayer() {
        player = new Player();
        player.setPlayerListener(e ->
                gameMenu.updateMenu(e.getTetromino(), e.getLevel(), e.getScore(), e.getLines())
        );
    }

    private void addNewGame() {
        gameFrame = new GameFrame(player);
        gc.gridx = 1;
        gc.gridy = 0;

        gc.weightx = 1;
        gc.weighty = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.fill = GridBagConstraints.BOTH;
        add(gameFrame, gc);
    }

    private void addMenu() {
        gameMenu = new GameMenu();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 0;
        gc.weighty = 0.1;
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
        add(gameMenu, gc);
    }

}
