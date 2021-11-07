package gui.menu.main;

import gui.EnterListener;
import staticAssets.Colors;
import keybinds.KeyBinder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


public class MainMenu extends JPanel {

    private EnterListener enterListener;
    private final GridBagConstraints gc;
    private LogoPanel logoPanel;
    private MenuDecoration decoration;
    private EnterGamePanel enterGamePanel;

    public MainMenu(int width, int height) {
        setBackground(Colors.BACKGROUND_COLOR);
        setPreferredSize(new Dimension(width, height));
        setLayout(new GridBagLayout());
        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        addLogoPanel();
        addTetromino();
        addEnterGameLabel();
        KeyBinder.addKeyBinding(this, KeyEvent.VK_ENTER, "ENTER", true, e -> {
            logoPanel.getTimer().stop();
            decoration.getTimer().stop();
            enterGamePanel.getTimer().stop();
            enterListener.enterEventOccurred();
        });
    }

    private void addLogoPanel() {
        logoPanel = new LogoPanel(getPreferredSize().width, getPreferredSize().height / 5);
        add(logoPanel, gc);
    }
    private void addTetromino() {
        decoration = new MenuDecoration(getPreferredSize().width, getPreferredSize().height / 10);
        gc.gridy = 1;
        add(decoration, gc);
    }
    private void addEnterGameLabel() {
        enterGamePanel = new EnterGamePanel(getPreferredSize().width, getPreferredSize().height / 10);
        gc.gridy = 2;
        add(enterGamePanel, gc);
    }

    public void setEnterListener(EnterListener listener) {
        this.enterListener = listener;
    }

}
