package gui.menu.settings;

import colors.Colors;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeybindingInput extends JDialog implements KeyListener {

    public KeybindingInput(Component parentComponent) {
        setUndecorated(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setPreferredSize(new Dimension(parentComponent.getWidth() / 2,parentComponent.getHeight() / 6));
        addKeyListener(this);
        Font titleFont = new Font("Serif", Font.BOLD, parentComponent.getWidth() / 15);

        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(Colors.BORDER_COLOR, 2));
        panel.setBackground(Colors.MISC_BACKGROUND);
        panel.setForeground(Colors.FOREGROUND_COLOR);

        JTextField keybindingField = new JTextField(1);
        keybindingField.setBackground(Colors.MISC_BACKGROUND);

        JLabel keybinding = new JLabel("Press a key");
        keybinding.setFont(titleFont);

        panel.add(keybinding);
        add(panel);

        pack();
        setLocationRelativeTo(parentComponent);
        setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Change the keybind here!
        System.out.println(e.getKeyCode());
        dispose();
    }

}
