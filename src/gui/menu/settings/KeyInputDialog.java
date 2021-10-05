package gui.menu.settings;

import colors.Colors;
import keybinds.KeyBinder;
import keybinds.KeybindingLoader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInputDialog extends JDialog implements KeyListener {

    private final String keybinding;
    private final JLabel keybindingLabel;
    private final JLabel label;

    public KeyInputDialog(Component parentComponent, String keybinding, JLabel keybindingLabel) {
        this.keybindingLabel = keybindingLabel;
        setUndecorated(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setPreferredSize(new Dimension(parentComponent.getWidth() / 2,parentComponent.getHeight() / 6));
        addKeyListener(this);

        Font titleFont = new Font("Serif", Font.BOLD, parentComponent.getWidth() / 20);

        this.keybinding = keybinding;
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(Colors.BORDER_COLOR, 2));
        panel.setBackground(Colors.MISC_BACKGROUND);
        panel.setForeground(Colors.FOREGROUND_COLOR);

        JTextField keybindingField = new JTextField(1);
        keybindingField.setBackground(Colors.MISC_BACKGROUND);

        label = new JLabel("Press a key");
        label.setFont(titleFont);
        label.setForeground(Colors.FOREGROUND_COLOR);

        panel.add(label);
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
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dispose();
        }
        else if (!KeybindingLoader.keybindingContains(e.getKeyCode())) {
            KeybindingLoader.setKeybinding(keybinding, e.getKeyCode());
            keybindingLabel.setText(KeyEvent.getKeyText(e.getKeyCode()));
            KeybindingLoader.saveKeybindings();
            dispose();
        } else {
            label.setText("<html><div style='text-align: center;'>Key is used<br/>Pick a different key</div></html>");
        }
    }

}
