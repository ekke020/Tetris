package gui.menu.settings;

import colors.Colors;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

import static colors.Colors.*;

public class SoundSlider extends JSlider {

    public SoundSlider(int width, String title) {
        super(JSlider.HORIZONTAL);
        setPreferredSize(new Dimension(width, 35));
        setBackground(BACKGROUND_COLOR);
        Font titleFont = new Font("Serif", Font.BOLD, width / 20);
        Border emptyBorder = BorderFactory.createEmptyBorder();
        setBorder(BorderFactory.createTitledBorder(emptyBorder, title,
                TitledBorder.LEFT, TitledBorder.ABOVE_TOP, titleFont, Colors.FOREGROUND_COLOR));

    }

}
