package gui.frames;

import colors.Colors;
import gui.menu.MenuButton;
import gui.menu.MenuListener;
import gui.menu.settings.BindingPanel;

import javax.swing.*;
import java.awt.*;

public class SettingsFrame extends JPanel {

    private MenuListener menuListener;
    private GridBagConstraints gc;

    public SettingsFrame() {
        setBackground(Colors.BACKGROUND_COLOR);
        setPreferredSize(new Dimension(784,961));

        setLayout(new GridBagLayout());

        addBindingPanel();
        addBackButton();
    }

    private void addBindingPanel() {
        BindingPanel bindingPanel = new BindingPanel();
        gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;

        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.NONE;

        gc.weightx = 1;
        gc.weighty = 0.1;

        add(bindingPanel, gc);
    }

    private void addBackButton() {
        MenuButton backButton = new MenuButton("Back");
        backButton.addActionListener(e -> menuListener.formEventOccurred(null));
        gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 1;

        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.fill = GridBagConstraints.NONE;

        gc.weightx = 1;
        gc.weighty = 0.1;

        add(backButton, gc);
    }
    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }
}
