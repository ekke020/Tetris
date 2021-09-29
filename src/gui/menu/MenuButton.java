package gui.menu;

import keybinds.KeyBinder;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuButton extends JButton implements ChangeListener {

    private final Color backgroundColor = new Color(107, 196, 255, 255);
    private final Color foreGroundColor = new Color(229, 232, 255, 200);
    private final Color rolloverBackgroundColor = new Color(78, 158, 206, 255);
    private final Color rolloverForegroundColor = new Color(183, 205, 244, 255);
    private final Color pressedColor = new Color(55, 109, 138, 255);

    public MenuButton(String text) {
        super(text);
        setFocusPainted(false);
        setOpaque(true);
        setRolloverEnabled(true);
        setBorder(BorderFactory.createEmptyBorder());

        setBackground(backgroundColor);
        setForeground(foreGroundColor);

        UIManager.put("Button.select", pressedColor);
        setFont(new Font("Serif", Font.BOLD, 26));

        addChangeListener(this);
        KeyBinder.removeKeyBinding(this, KeyEvent.VK_SPACE, "none", false);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (getModel().isPressed()) {
            setBackground(pressedColor);
        }
        else if (getModel().isRollover()) {
            setBackground(rolloverBackgroundColor);
            setForeground(rolloverForegroundColor);
        } else {
            setBackground(backgroundColor);
            setForeground(foreGroundColor);
        }
    }

}
