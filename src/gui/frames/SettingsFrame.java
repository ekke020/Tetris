package gui.frames;

import colors.Colors;
import gui.ComponentResizeListener;
import gui.menu.MenuButton;
import gui.menu.MenuListener;
import gui.menu.settings.BindingPanel;

import javax.swing.*;
import java.awt.*;

// TODO: Add different color modes.

public class SettingsFrame extends JPanel {

    private MenuListener menuListener;
    private MenuButton backButton;
    private BindingPanel bindingPanel;
    private GridBagConstraints gc;

    public SettingsFrame(int width, int height) {
        setBackground(Colors.BACKGROUND_COLOR);
        setPreferredSize(new Dimension(width, height));

        setLayout(new GridBagLayout());

        addBindingPanel(width / 3, height / 3);
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
        gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;

        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.NONE;

        gc.weightx = 1;
        gc.weighty = 0.1;
        add(bindingPanel, gc);
    }

    private void addBackButton(int buttonWidth, int buttonHeight) {
        backButton = new MenuButton("Back",buttonWidth, buttonHeight);
        backButton.addActionListener(e -> menuListener.formEventOccurred(null));
        gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 1;

        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.fill = GridBagConstraints.NONE;

        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.weightx = 1;
        gc.weighty = 0.1;

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

