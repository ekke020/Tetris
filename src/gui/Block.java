package gui;

import staticAssets.Fonts;
import tetromino.Tetromino;

import javax.swing.*;
import java.awt.*;

public class Block extends JPanel {

    private Tetromino tetromino;
    private final int blockRow;
    private final int blockColumn;
    private boolean hide = false;
    private String scoreNumber = "0";
    private boolean showScoreNumber = false;

    public int getBlockRow() {
        return blockRow;
    }

    public void setTetromino(Tetromino tetromino) {
        this.tetromino = tetromino;
    }

    public Tetromino getTetromino() {
        return tetromino;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public void setScoreNumber(String num) {
        scoreNumber = num;
    }

    public void setShowScoreNumber(boolean showScoreNumber) {
        this.showScoreNumber = showScoreNumber;
    }

    public Block(int blockRow, int blockColumn) {
        this.blockColumn = blockColumn;
        this.blockRow = blockRow;
        setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

//        g2.setFont(new Font("TimesRoman", Font.PLAIN, 8));
//        String blockCol = "C:" + blockColumn;
//        g2.drawString(blockCol, getWidth()/2, getHeight()/2);
//        String blockR = "R:" + blockRow;
//        g2.drawString(blockR, getWidth()/2, (getHeight()/4));

        int widthIncrement = getWidth() / 10;
        int heightIncrement = getHeight() / 10;
        int width = getWidth() - widthIncrement;
        int height = getHeight() - heightIncrement;
        if (tetromino != null && !hide) {
            g2.setColor(tetromino.getColor());
            g2.draw3DRect(widthIncrement, heightIncrement, width, height, true);
            g2.fill3DRect(widthIncrement, heightIncrement, width, height, true);
        }
        if (showScoreNumber) {
            drawCenteredString(g2, scoreNumber, getWidth(), getHeight());
        }
    }

    private void drawCenteredString(Graphics2D g2, String num, int width, int height) {
        FontMetrics fm = g2.getFontMetrics();
        int x = (int) (((width - fm.stringWidth(num)) / 2) * 0.6);
        int y = ((int) (fm.getAscent() + (height - (fm.getAscent() + fm.getDescent())) * 0.8));
        g2.setFont(Fonts.getFont(Fonts.TEXT_FONT).deriveFont(getHeight() / 2F));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawString(num, x, y);
    }
}
