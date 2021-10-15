package gui.menu.main;

import colors.Colors;
import gui.ComponentResizeListener;
import gui.menu.MenuButton;
import gui.menu.MenuClicks;
import gui.menu.MenuListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;


public class MainMenu extends JPanel {

    private MenuListener menuListener;
    private final GridBagConstraints gc;
    private final MenuButton[] buttonList = new MenuButton[3];
    private final int buttonWidth;
    private final int buttonHeight;
    
    public MainMenu(int width, int height) {
        setBackground(Colors.BACKGROUND_COLOR);
        setPreferredSize(new Dimension(width, height));
        buttonWidth = width / 3;
        buttonHeight = height / 16;
        setLayout(new GridBagLayout());
        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        addLogo();
        addBackgroundImage();
        addNewGameButton();
        addOptionsButton();
        addHighScoreButton();
        addComponentListener(new ComponentResizeListener() {
            @Override
            public void resizeTimedOut() {
                setComponentSizes();
            }
        });
    }

    private void addLogo() {
        Path path = Paths.get("assets/Tetris_Logo.png");
        ImageLabel tetrisLogo = new ImageLabel(path, 759, 221);
        add(tetrisLogo, gc);
    }

    private void addNewGameButton() {
        MenuButton button = new MenuButton("New Game", buttonWidth, buttonHeight);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuClicks mc = new MenuClicks(this, 0);
                menuListener.formEventOccurred(mc);
            }
        });
        buttonList[0] = button;
        gc.gridy = 1;
        gc.weighty = 0.01;
        add(button, gc);
    }

    private void addOptionsButton() {
        MenuButton button = new MenuButton("Options", buttonWidth, buttonHeight);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuClicks mc = new MenuClicks(this, 1);
                menuListener.formEventOccurred(mc);
            }
        });
        buttonList[1] = button;
        gc.gridy = 2;
        add(button, gc);
    }

    private void addHighScoreButton() {
        MenuButton button = new MenuButton("High score", buttonWidth, buttonHeight);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuClicks mc = new MenuClicks(this, 2);
                menuListener.formEventOccurred(mc);
            }
        });
        buttonList[2] = button;
        gc.gridy = 3;
        gc.weighty = 0.1;
        add(button, gc);
    }

    private void addBackgroundImage() {
        Path path = Paths.get("assets/MainMenuImage.png");
        ImageLabel backgroundImage = new ImageLabel(path, 707, 213);
        gc.anchor = GridBagConstraints.PAGE_START;
        gc.gridy = 4;
        gc.weighty = 0.6;
        add(backgroundImage, gc);
    }

    private void setComponentSizes() {
        for (MenuButton button : buttonList){
            button.setPreferredSize(new Dimension(getWidth() / 3, getHeight() / 16));
        }
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }

}
