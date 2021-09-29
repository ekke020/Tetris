package gui.menu.game;

import javax.swing.*;
import java.awt.*;

public class TextField extends JTextField {


    public TextField(String title, int topSize, int bottomSize, int leftSize, int rightSize, int alignment) {
        super(title, 6);
        setEditable(false);
        setFocusable(false);
        Color scoreColor = new Color(91, 224, 247, 255);
        setBorder(BorderFactory.createMatteBorder(topSize, leftSize, bottomSize, rightSize, scoreColor));
        setFont(new Font("Serif", Font.BOLD, 26));
        setBackground(new Color(5, 226, 255, 255));
        setForeground(new Color(229, 232, 255, 200));
        setHorizontalAlignment(alignment);
    }
}
