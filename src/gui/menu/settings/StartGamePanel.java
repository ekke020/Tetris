package gui.menu.settings;

import staticAssets.Colors;
import staticAssets.Fonts;

import javax.swing.*;
import java.awt.*;

public class StartGamePanel extends JPanel {

    private final JLabel topLabel = new JLabel("To start");
    private final JLabel bottomLabel = new JLabel("press ENTER");
    private final JLabel[] labels = new JLabel[] {topLabel, bottomLabel};

    public StartGamePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        add(topLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(bottomLabel);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int size = (getWidth() / 26);
        for (JLabel label : labels) {
            label.setFont(Fonts.getFont(Fonts.TEXT_FONT).deriveFont(Font.PLAIN, size));
            label.setForeground(Colors.FOREGROUND_COLOR);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
    }
}
