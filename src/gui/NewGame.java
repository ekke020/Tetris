package gui;

import gui.frames.GameFrame;
import gui.menu.game.GameMenu;
import manager.GameState;
import player.Player;

import javax.swing.*;
import java.awt.*;

public class NewGame extends JPanel {

    private final GridBagConstraints gc;

    private GameMenu gameMenu;
    private GameFrame gameFrame;
    private Player player;

    public NewGame() {
        setLayout(new GridBagLayout());
        gc = new GridBagConstraints();

        addMenu();
        addNewPlayer();
        addNewGame();
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
        gc.fill = GridBagConstraints.BOTH;

        add(gameMenu, gc);
    }

}
