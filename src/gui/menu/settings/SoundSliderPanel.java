package gui.menu.settings;

import colors.Colors;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SoundSliderPanel extends JPanel {

    public SoundSliderPanel(int width, int height) {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(width, height));
        setOpaque(false);

        Font titleFont = new Font("Serif", Font.BOLD, width / 15);
        setBorder(BorderFactory.createTitledBorder(
                this.getBorder(),"Sound options", TitledBorder.LEFT,
                TitledBorder.ABOVE_TOP, titleFont, Colors.FOREGROUND_COLOR));
        addSlider(width - 10, "Sound effects", 0);
        addSlider(width - 10, "Menu music", 1);
        addSlider(width - 10, "Game music", 2);
    }

    private void addSlider(int width, String title, int gridy) {
        SoundSlider slider = new SoundSlider(width, title);
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = gridy;

        add(slider, gc);
    }
}
