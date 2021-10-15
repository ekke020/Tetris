package gui.menu.settings;

import colors.Colors;
import sound.AudioPlayer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SoundSliderPanel extends JPanel {

    private final SoundSlider[] soundSliderList = new SoundSlider[2];

    public SoundSliderPanel(int width, int height) {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(width, height));
        setOpaque(false);

        Font titleFont = new Font("Serif", Font.BOLD, width / 15);
        setBorder(BorderFactory.createTitledBorder(
                this.getBorder(),"Sound options", TitledBorder.LEFT,
                TitledBorder.ABOVE_TOP, titleFont, Colors.FOREGROUND_COLOR));
        addSlider(width - 10, "Sound effects", 0, AudioPlayer.getMiscVolume());
        addSlider(width - 10, "Background music", 1, AudioPlayer.getBackgroundVolume());
    }

    private void addSlider(int width, String title, int gridy, float value) {
        SoundSlider slider = new SoundSlider(width, title, value);
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = gridy;

        slider.addChangeListener(e -> {
            int soundLevel = slider.getValue();
            AudioPlayer.updateVolumes(soundLevel, gridy);
        });
        soundSliderList[gridy] = slider;
        add(slider, gc);
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        int width = (int) preferredSize.getWidth() - 10;
        if (this.getBorder() != null) {
            int size = (int) (preferredSize.getWidth() / 15);
            ((javax.swing.border.TitledBorder) getBorder()).setTitleFont(new Font("Serif", Font.BOLD, size));
            for (SoundSlider soundSlider : soundSliderList) {
                soundSlider.setPreferredSize(new Dimension(width, 35));
            }
        }
    }
}
