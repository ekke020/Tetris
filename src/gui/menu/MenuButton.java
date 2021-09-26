package gui.menu;

import keybinds.KeyBinder;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuButton extends JButton implements ChangeListener {

    public MenuButton(String text) {
        super(text);
        setFocusPainted(false);
        setOpaque(true);
        setRolloverEnabled(true);
        setBorder(BorderFactory.createEmptyBorder());

        setBackground(new Color(107, 196, 255, 255));
        setForeground(new Color(229, 232, 255, 200));
        setFont(new Font("Serif", Font.BOLD, 26));

        addChangeListener(this);
        KeyBinder.removeKeyBinding(this, KeyEvent.VK_SPACE, "none", false);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (getModel().isPressed()) {
            setBackground(new Color(70, 140, 182, 255));
        } else if (getModel().isRollover()) {
            setBackground(new Color(78, 158, 206, 255));
            setForeground(new Color(183, 205, 244, 255));
        } else {
            setBackground(new Color(107, 196, 255, 255));
            setForeground(new Color(229, 232, 255, 200));
        }
    }

}
