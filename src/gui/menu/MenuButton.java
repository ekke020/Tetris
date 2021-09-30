package gui.menu;

import colors.Colors;
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

        setBackground(Colors.BUTTON_BACKGROUND_COLOR);
        setForeground(Colors.BUTTON_FOREGROUND_COLOR);

        UIManager.put("Button.select", Colors.PRESSED_COLOR);
        setFont(new Font("Serif", Font.BOLD, 26));

        addChangeListener(this);
        KeyBinder.removeKeyBinding(this, KeyEvent.VK_SPACE, "none", false);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (getModel().isPressed()) {
            setBackground(Colors.PRESSED_COLOR);
        }
        else if (getModel().isRollover()) {
            setBackground(Colors.ROLLOVER_BACKGROUND_COLOR);
            setForeground(Colors.ROLLOVER_FOREGROUND_COLOR);
        } else {
            setBackground(Colors.BUTTON_BACKGROUND_COLOR);
            setForeground(Colors.BUTTON_FOREGROUND_COLOR);
        }
    }

}
