package gui.frames;

import colors.Colors;
import gui.ComponentResizeListener;
import gui.menu.MenuButton;
import gui.menu.MenuListener;
import gui.menu.settings.BindingPanel;
import gui.menu.settings.SoundSliderPanel;

import javax.swing.*;
import java.awt.*;

// TODO: Add different color modes.

public class SettingsFrame extends JPanel {

    private MenuListener menuListener;
    private MenuButton backButton;
    private BindingPanel bindingPanel;
    private SoundSliderPanel soundSliderPanel;
    private GridBagConstraints gc;

    public SettingsFrame(int width, int height) {
        setBackground(Colors.BACKGROUND_COLOR);

        setLayout(new GridBagLayout());
        gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 0.1;

        addBindingPanel(width / 3, height / 3);
        addSoundSliderPanel(width / 2, height / 6);
        addBackButton(width / 3, height / 16);
        addComponentListener(new ComponentResizeListener() {
            @Override
            public void resizeTimedOut() {
                setComponentSizes();
            }
        });
    }

    private void addBindingPanel(int width, int height) {
        bindingPanel = new BindingPanel(width, height);

        add(bindingPanel, gc);
    }

    private void addSoundSliderPanel(int width, int height) {
        soundSliderPanel = new SoundSliderPanel(width, height);
        gc.gridy = 1;
        add(soundSliderPanel, gc);
    }

    private void addBackButton(int buttonWidth, int buttonHeight) {
        backButton = new MenuButton("Back",buttonWidth, buttonHeight);
        backButton.addActionListener(e -> menuListener.formEventOccurred(null));

        gc.gridy = 2;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.gridwidth = GridBagConstraints.REMAINDER;

        add(backButton, gc);
    }

    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }

    private void setComponentSizes() {
        // Update all the component sizes here.
        System.out.println("SettingsFrame");
        backButton.setPreferredSize(new Dimension(getWidth() / 3, getHeight() / 16));
        bindingPanel.setPreferredSize(new Dimension(getWidth() / 3, getHeight() / 3));
        SwingUtilities.updateComponentTreeUI(this);
    }
}

