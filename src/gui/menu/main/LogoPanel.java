package gui.menu.main;


import staticAssets.Fonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogoPanel extends JPanel implements ActionListener {

    private final JLabel[] logo = {
            new JLabel("t"), new JLabel("e"), new JLabel("t"),
            new JLabel("r"), new JLabel("i"), new JLabel("s")
    };
    private final Color[] colors = {
            Color.BLUE, Color.CYAN, Color.ORANGE,
            Color.RED, Color.GREEN, Color.MAGENTA, Color.YELLOW
    };
    private final Timer timer = new Timer(250, this);
    private int logoIndex = 1;
    private int colorIndex = 1;

    public Timer getTimer() {
        return timer;
    }

    public LogoPanel(int width, int height) {
        setOpaque(false);
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout());
        Font tetrisFont = Fonts.getFont(Fonts.MAIN_MENU_FONT);
        for (JLabel jLabel : logo) {
            jLabel.setFont(tetrisFont);
            jLabel.setForeground(colors[0]);
            add(jLabel);
        }
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < logoIndex; i++) {
            logo[i].setForeground(colors[colorIndex]);
        }
        logoIndex++;
        if (logoIndex > 6) {
            logoIndex = 0;
            colorIndex++;
            if (colorIndex > 6)
                colorIndex = 0;
        }
    }
}
