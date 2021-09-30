package gui.menu.settings;

import gui.menu.MenuButton;
import keybinds.KeybindingLoader;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;

public class BindingPanel extends JPanel {

    Color foreGround = new Color(230, 230, 230, 255);

    public BindingPanel() {
        setOpaque(false);
        Font titleFont = new Font("Serif", Font.BOLD, 26);
        setBorder(BorderFactory.createTitledBorder(this.getBorder(),"Keybindings", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, titleFont, foreGround));
        setLayout(new GridLayout(5,1));

        addRow("Move left","MOVE_LEFT");
        addRow("Move right","MOVE_RIGHT");
        addRow("Spin","SPIN");
        addRow("Soft drop","SOFT_DROP");
        addRow("hard drop","HARD_DROP");

    }

    private void addRow(String text, String keybind) {
        JPanel row = new JPanel();
        row.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        row.setOpaque(false);

        String key = KeyEvent.getKeyText(KeybindingLoader.getKeybinding(keybind));
        JTextField information = new JTextField(14);

        information.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230, 255), 5));
        information.setText(text + "\t=> " + key);

        information.setFont(new Font("Serif", Font.BOLD, 26));
        information.setForeground(new Color(230, 230, 230, 255));
        information.setBackground(new Color(64, 64, 64, 255));

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
