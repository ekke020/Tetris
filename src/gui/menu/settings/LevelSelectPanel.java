package gui.menu.settings;

import keybinds.KeyBinder;
import staticAssets.Colors;
import staticAssets.Fonts;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

public class LevelSelectPanel extends JPanel implements FocusListener {

    private ReturnListener returnListener;
    private final LevelPanel[] topPanels = new LevelPanel[5];
    private final LevelPanel[] bottomPanels = new LevelPanel[5];
    private LevelPanel target;
    private int index = 0;
    private int row = 0;

    public LevelSelectPanel() {
        setOpaque(false);
        setLayout(new GridLayout(2,5));
        setPreferredSize(new Dimension(400, 200));
        populateGrid();
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(),"LEVELS", TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION, Fonts.getFont(Fonts.TEXT_FONT).deriveFont(Font.PLAIN,
                        15F), Colors.FOREGROUND_COLOR));
        addFocusListener(this);
        target = topPanels[0];
        updateFocus();
    }

    private void populateGrid() {

        for (int i = 0; i < topPanels.length; i++) {
            topPanels[i] = new LevelPanel(i);
            bottomPanels[i] = new LevelPanel(i + 5);
            add(topPanels[i]);
        }
        for (LevelPanel bottomPanel : bottomPanels) {
            add(bottomPanel);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        System.out.println("Gained focus");
        KeyBinder.addKeyBinding(this, KeyEvent.VK_DOWN, "DOWN", true, f -> {
            changeRow();
            updateTarget();
        });
        KeyBinder.addKeyBinding(this, KeyEvent.VK_UP, "UP", true, f -> {
            changeRow();
            updateTarget();
        });
        KeyBinder.addKeyBinding(this, KeyEvent.VK_LEFT, "LEFT", true, f -> {
            updateIndex(-1);
        });
        KeyBinder.addKeyBinding(this, KeyEvent.VK_RIGHT, "RIGHT", true, f -> {
            updateIndex(1);
        });
        KeyBinder.addKeyBinding(this, KeyEvent.VK_ESCAPE, "BACK", true, f -> {
            returnListener.returnEventOccurred();
        });
    }

    @Override
    public void focusLost(FocusEvent e) {
        KeyBinder.removeKeyBinding(this, KeyEvent.VK_DOWN, true);
        KeyBinder.removeKeyBinding(this, KeyEvent.VK_UP, true);
        KeyBinder.removeKeyBinding(this, KeyEvent.VK_LEFT, true);
        KeyBinder.removeKeyBinding(this, KeyEvent.VK_RIGHT, true);
        KeyBinder.removeKeyBinding(this, KeyEvent.VK_ESCAPE, true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int size =  (getWidth() / 26);
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(),"LEVELS", TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION, Fonts.getFont(Fonts.TEXT_FONT).deriveFont(Font.PLAIN,
                        size), Colors.FOREGROUND_COLOR));
    }

    private void updateIndex(int num) {
        index += num;
        if (index < 0) {
            index = 4;
            changeRow();
        }
        if (index > 4) {
            index = 0;
            changeRow();
        }
        updateTarget();
    }

    private void changeRow() {
        if (row == 0)
            row = 1;
        else
            row = 0;
    }

    private void updateTarget() {
        target.focus = false;
        if (row == 0) {
            target = topPanels[index];
        } else {
            target = bottomPanels[index];
        }
        updateFocus();
    }
    private void updateFocus() {
        target.focus = true;
        repaint();
    }

    public void setReturnListener(ReturnListener listener) {
        returnListener = listener;
    }

    public int getLevel() {
        return target.level;
    }

    private static class LevelPanel extends JPanel{

        private final int level;
        private boolean focus = false;

        public LevelPanel(int level) {
            this.level = level;
            setOpaque(false);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2 = (Graphics2D) g;
            int size = getWidth() / 2;
            if (focus) {
                g2.setColor(Colors.LEVEL_SELECTED_COLOR);
            } else {
                g2.setColor(Colors.LEVEL_SELECT_COLOR);
            }
            g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, size));
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            drawCenteredString(g2, level, getWidth(), getHeight());
        }

        private void drawCenteredString(Graphics2D g2, int num, int width, int height) {
            String number = String.valueOf(num);
            FontMetrics fm = g2.getFontMetrics();
            int x = (width - fm.stringWidth(number)) / 2;
            int y = ((int) (fm.getAscent() + (height - (fm.getAscent() + fm.getDescent())) * 0.47));
            g2.drawString(number, x, y);
        }
    }
}
