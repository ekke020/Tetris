package gui;

import gui.frames.SettingsFrame;
import gui.menu.main.MainMenu;
import keybinds.KeybindingLoader;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private final GridBagConstraints gc;

    private MainMenu mainMenu;
    private SettingsFrame settingsFrame;

    public Window() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 1000));

        ImageIcon image = new ImageIcon("assets/FrameIcon.jpg");
        setIconImage(image.getImage());

        KeybindingLoader.loadKeybindings();
        setLayout(new GridBagLayout());
        gc = new GridBagConstraints();

        pack();
        addMainMenu();
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private void addMainMenu() {
        mainMenu = new MainMenu(getWidth() - 16, getHeight() - 39);
        mainMenu.setMenuListener(e -> {
            switch (e.getId()) {
                case 0 -> {
                    remove(mainMenu);
                    addNewGame();
                }
                case 1 -> {
                    remove(mainMenu);
                    addSettings();
                }

            }
            revalidate();
            repaint();
        });
        gc.gridx = 0;
        gc.gridy = 0;

        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 0.1;

        add(mainMenu, gc);
    }

    private void addNewGame() {
        NewGame newGame = new NewGame();

        gc.gridx = 0;
        gc.gridy = 0;

        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 0.1;

        add(newGame, gc);
    }

    private void addSettings() {
        settingsFrame = new SettingsFrame(getWidth() - 16, getHeight() - 39);
        settingsFrame.setMenuListener(e -> {
            remove(settingsFrame);
            addMainMenu();
            revalidate();
            repaint();
        });
        gc.gridx = 0;
        gc.gridy = 0;

        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 0.1;

        add(settingsFrame, gc);
    }
}