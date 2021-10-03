package gui.menu;

import colors.Colors;
import keybinds.KeyBinder;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuButton extends JButton implements ChangeListener {

    public MenuButton(String text, int width, int height) {
        super(text);
        setFocusPainted(false);
        setOpaque(true);
        setRolloverEnabled(true);
        setBorder(BorderFactory.createEmptyBorder());
        setPreferredSize(new Dimension(width, height));

        setBackground(Colors.BUTTON_BACKGROUND_COLOR);
        setForeground(Colors.BUTTON_FOREGROUND_COLOR);

        UIManager.put("Button.select", Colors.PRESSED_COLOR);

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

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        int size = (int) (preferredSize.getWidth() / 10);
        setFont(new Font("Serif", Font.BOLD, size));
    }

}
