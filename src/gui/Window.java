package gui;

import gui.frames.SettingsFrame;
import gui.menu.main.MainMenu;
import keybinds.KeybindingLoader;
import sound.AudioPlayer;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private MainMenu mainMenu;
    private SettingsFrame settingsFrame;

    public Window() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 1000));

        ImageIcon image = new ImageIcon("assets/FrameIcon.jpg");
        setIconImage(image.getImage());

        KeybindingLoader.loadKeybindings();

        pack();
        addMainMenu();
        setLocationRelativeTo(null);

        setVisible(true);
        AudioPlayer.play(AudioPlayer.MENU);
    }

    private void addMainMenu() {
        mainMenu = new MainMenu(getWidth() - 16, getHeight() - 39);
        mainMenu.setEnterListener(() -> {
            remove(mainMenu);
            addSettings();
            revalidate();
            repaint();
        });
        add(mainMenu);
    }

    private void addNewGame(int level) {
        NewGame newGame = new NewGame(getWidth() - 16, getHeight() - 39, level);
        add(newGame);
    }

    private void addSettings() {
        settingsFrame = new SettingsFrame();
        settingsFrame.setNewGameListener(e -> {
            remove(settingsFrame);
            addNewGame(e);
            revalidate();
            repaint();
        });
        add(settingsFrame);
    }
}