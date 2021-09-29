package gui.menu.main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TetrisLogo extends JLabel {

    private BufferedImage image;
    private final String imagePath = "assets/The_Tetris_Company_logo.png";


    public TetrisLogo() {
        setMaximumSize(new Dimension(373, 259));
        setPreferredSize(new Dimension(373, 259));
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}
