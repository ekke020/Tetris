package gui.frames;

import gui.NewGameListener;
import gui.menu.settings.*;
import staticAssets.Colors;
import gui.ComponentResizeListener;
import keybinds.KeyBinder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class SettingsFrame extends JPanel {

    private SelectedOptionPanel optionPanel;

    private NewGameListener newGameListener;
    private final GridBagConstraints gc;
    private final MenuOption[] panels = new MenuOption[4];

    private int index = 0;
    public SettingsFrame() {
        setBackground(Colors.BACKGROUND_COLOR);

        setLayout(new GridBagLayout());
        gc = new GridBagConstraints();


        addKeyBinding();
        addVolume();
        addLevelSelect();
        addStartGame();
        addSelectedOptionPanel();
        addComponentListener(new ComponentResizeListener() {
            @Override
            public void resizeTimedOut() {
                setComponentSizes();
            }
        });
        setKeyBindings();
    }

    private void addKeyBinding() {
        MenuOption option = new MenuOption("KEYBINDING");
        option.addSettingsListener(() -> {
            optionPanel.addBindingPanel();
        });
        option.addEnterListener(() -> {
            optionPanel.focusPanel();
        });
        panels[0] = option;
        gc.weighty = 1;
        gc.anchor = GridBagConstraints.PAGE_END;
        add(option, gc);
    }

    private void addVolume() {
        MenuOption option = new MenuOption("VOLUME");
        option.addSettingsListener(() -> {
            optionPanel.addSoundSliderPanel();
        });
        option.addEnterListener(() -> {
            optionPanel.focusPanel();
        });
        panels[1] = option;
        gc.gridy = 1;
        gc.weighty = 0;
        gc.anchor = GridBagConstraints.PAGE_START;
        add(option, gc);
    }

    private void addLevelSelect() {
        MenuOption option = new MenuOption("LEVEL SELECT");
        option.addSettingsListener(() -> {
            optionPanel.addLevelSelectPanel();
        });
        option.addEnterListener(() -> {
            optionPanel.focusPanel();
        });
        panels[2] = option;
        gc.gridy = 2;
        add(option, gc);
    }

    private void addStartGame() {
        MenuOption option = new MenuOption("START GAME");
        option.addSettingsListener(() -> {
            optionPanel.addStartGamePanel();
        });
        option.addEnterListener(() -> {
            int level = optionPanel.getSelectedLevel();
            newGameListener.newGameEventOccurred(level);
        });
        panels[3] = option;
        gc.gridy = 3;
        gc.weighty = 0.5;
        add(option, gc);
    }

    private void addSelectedOptionPanel() {
        optionPanel = new SelectedOptionPanel();
        optionPanel.addReturnListener(() -> {
            panels[index].requestFocusInWindow();
            setKeyBindings();
        });
        optionPanel.addListenerToPanels();
        gc.gridy = 4;
        gc.anchor = GridBagConstraints.CENTER;
        add(optionPanel, gc);
    }

    private void setKeyBindings() {
        KeyBinder.addKeyBinding(this, KeyEvent.VK_DOWN, "DOWN", true, e -> {
            index++;
            if (index == panels.length)
                index = 0;
            panels[index].requestFocusInWindow();
        });
        KeyBinder.addKeyBinding(this, KeyEvent.VK_UP, "UP", true, e -> {
            index--;
            if (index == -1)
                index = panels.length - 1;
            panels[index].requestFocusInWindow();
        });
    }

    public void setNewGameListener(NewGameListener listener) {
        this.newGameListener = listener;
    }

    private void setComponentSizes() {
        panels[index].requestFocusInWindow();
        int width = getWidth() / 2;
        if (width > 500)
            width = 500;
        optionPanel.setPreferredSize(new Dimension(width, getHeight() / 4));
        for (MenuOption panel : panels) {
            panel.setPreferredSize(new Dimension(width, getHeight() / 24));
        }
        SwingUtilities.updateComponentTreeUI(this);
    }

}

