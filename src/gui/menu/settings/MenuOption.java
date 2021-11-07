package gui.menu.settings;

import gui.EnterListener;
import staticAssets.Colors;
import staticAssets.Fonts;
import keybinds.KeyBinder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuOption extends JPanel implements FocusListener {

    private final String title;
    private SettingsListener settingsListener;
    private EnterListener enterListener;
    private JLabel label;
    private final Font font = Fonts.getFont(Fonts.TEXT_FONT);

    public MenuOption(String title) {
        this.title = title;
        setName(title);
        setRequestFocusEnabled(true);
        requestFocusInWindow();
        setOpaque(false);
        setPreferredSize(new Dimension(400, 40));
        addFocusListener(this);
        addLabel();
    }

    private void addLabel() {
        label = new JLabel(title);
        label.setFont(font);
        label.setForeground(Colors.FOREGROUND_COLOR);
        add(label);
    }

    @Override
    public void focusGained(FocusEvent e) {
        KeyBinder.addKeyBinding(this, KeyEvent.VK_ENTER, "ENTER", true, f -> {
            System.out.println(getName() + " pressed ENTER!");
            enterListener.enterEventOccurred();
        });
        settingsListener.menuEventOccurred();
        setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR));
        repaint();
    }

    @Override
    public void focusLost(FocusEvent e) {
        KeyBinder.removeKeyBinding(this, KeyEvent.VK_ENTER, true);
        setBorder(BorderFactory.createEmptyBorder());
        repaint();
    }

    public void addSettingsListener(SettingsListener listener) {
        this.settingsListener = listener;
    }

    public void addEnterListener(EnterListener listener) {
        this.enterListener = listener;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int size =  (getHeight() / 2);
        label.setFont(font.deriveFont(Font.PLAIN, size));
    }
}
