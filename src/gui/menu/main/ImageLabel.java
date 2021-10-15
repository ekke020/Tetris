package gui.menu.main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class TetrisLogo extends JLabel {

    private BufferedImage image;


    public TetrisLogo(Path path, int width, int height) {
        setMaximumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        try {
            image = ImageIO.read(new File(String.valueOf(path)));
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
