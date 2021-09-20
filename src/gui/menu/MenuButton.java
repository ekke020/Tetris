package gui.menu;

import manager.KeyBinder;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuButton extends JButton implements ChangeListener {

    public MenuButton(String text) {
        super(text);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(true);
        setRolloverEnabled(true);
        setBackground(new Color(107, 196, 255, 255));
        setForeground(new Color(229, 232, 255, 200));
        setFont(new Font("Serif", Font.BOLD, 26));
        addChangeListener(this);
        KeyBinder.removeKeyBinding(this, KeyEvent.VK_SPACE, "none", false);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (getModel().isPressed()) {
            setBackground(new Color(19, 33, 171, 224));
        } else if (getModel().isRollover()) {
            setBackground(new Color(34, 50, 224, 224));
            setForeground(new Color(183, 205, 244, 255));
        } else {
            setBackground(new Color(107, 196, 255, 255));
            setForeground(new Color(229, 232, 255, 200));
        }

    }
}
