package gui.menu.game;

import staticAssets.Colors;

import javax.swing.*;
import java.awt.*;

public class TextField extends JTextField {


    public TextField(String title, int topSize, int bottomSize, int leftSize, int rightSize, int alignment) {
        super(title, 6);
        setEditable(false);
        setFocusable(false);
        setBorder(BorderFactory.createMatteBorder(topSize, leftSize, bottomSize, rightSize, Colors.BORDER_COLOR));
        setFont(new Font("Serif", Font.BOLD, 24));
        setBackground(Colors.MISC_BACKGROUND);
        setForeground(Colors.FOREGROUND_COLOR);
        setHorizontalAlignment(alignment);
    }
}
