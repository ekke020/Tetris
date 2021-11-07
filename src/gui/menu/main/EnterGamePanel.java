package gui.menu.main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static staticAssets.Colors.FOREGROUND_COLOR;

public class EnterGamePanel extends JPanel implements ActionListener {

    private boolean hide = false;
    private Timer timer = new Timer(500, this);
    private JLabel label = new JLabel("Press Enter");

    public Timer getTimer() {
        return timer;
    }

    public EnterGamePanel(int width, int height) {
        setOpaque(false);
        setPreferredSize(new Dimension(width, height));
        label.setFont(new Font("Serif", Font.BOLD, 50));
        label.setForeground(FOREGROUND_COLOR);
        add(label);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (hide)
            label.setText("Press ENTER");
        else
            label.setText("");
        hide = !hide;
    }
}
