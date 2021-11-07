package gui.menu.main;


import staticAssets.Fonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class MenuDecoration extends JPanel implements ActionListener {

    private final java.util.List<JLabel> tetrominoLabel = new LinkedList<>(java.util.List.of(
            new JLabel("D"), new JLabel("A"), new JLabel("C"),
            new JLabel("F"), new JLabel("G"), new JLabel("B"), new JLabel("E")));
    private final Timer timer = new Timer(1800, this);

    public Timer getTimer() {
        return timer;
    }

    public MenuDecoration(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout());
        setOpaque(false);
        Color[] colors = {
                Color.BLUE, Color.YELLOW, Color.MAGENTA,
                Color.GREEN, Color.RED, Color.ORANGE, Color.CYAN
        };
        Font tetrisFont = Fonts.getFont(Fonts.MAIN_MENU_FONT).deriveFont(100f);
        for (int i = 0; i < tetrominoLabel.size(); i++) {
            tetrominoLabel.get(i).setFont(tetrisFont);
            tetrominoLabel.get(i).setForeground(colors[i]);
            if (i < 5)
                add(tetrominoLabel.get(i));
        }
        fixList();
        timer.start();
    }

    private void fixList() {
        JLabel temp = tetrominoLabel.get(tetrominoLabel.size() - 1);
        tetrominoLabel.remove(6);
        tetrominoLabel.add(0, temp);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        removeAll();
        for (int i = 0; i < 5; i++) {
            add(tetrominoLabel.get(i));
        }
        revalidate();
        repaint();
        fixList();
    }
}
