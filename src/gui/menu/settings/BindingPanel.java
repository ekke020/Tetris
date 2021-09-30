package gui.menu.settings;

import colors.Colors;
import gui.menu.MenuButton;
import keybinds.KeybindingLoader;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;

public class BindingPanel extends JPanel {


    public BindingPanel() {
        setOpaque(false);
        Font titleFont = new Font("Serif", Font.BOLD, 26);
        setBorder(BorderFactory.createTitledBorder(
                this.getBorder(),"Keybindings", TitledBorder.LEFT,
                TitledBorder.ABOVE_TOP, titleFont, Colors.FOREGROUND_COLOR));

        setLayout(new GridLayout(5,1));

        addRow("Move left","MOVE_LEFT");
        addRow("Move right","MOVE_RIGHT");
        addRow("Spin","SPIN");
        addRow("Soft drop","SOFT_DROP");
        addRow("hard drop","HARD_DROP");

    }

    private void addRow(String text, String keybinding) {
        JPanel row = new JPanel();
        row.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        row.setOpaque(false);

        String key = KeyEvent.getKeyText(KeybindingLoader.getKeybinding(keybinding));
        JTextField information = new JTextField(14);

        information.setBorder(BorderFactory.createLineBorder(Colors.FOREGROUND_COLOR, 5));
        information.setText(text + "\t=> " + key);

        information.setFont(new Font("Serif", Font.BOLD, 26));
        information.setForeground(Colors.FOREGROUND_COLOR);
        information.setBackground(Colors.BACKGROUND_COLOR);

        information.setEditable(false);
        information.setFocusable(false);

        row.add(information);
        add(row);

        MenuButton rebind = new MenuButton("Rebind");
        rebind.setPreferredSize(new Dimension(20, 20));
        rebind.addActionListener(e -> {
            System.out.println(key);
        });
        add(rebind);
    }
}
