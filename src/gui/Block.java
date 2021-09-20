package gui;

import tetromino.Tetromino;

import javax.swing.*;
import java.awt.*;

public class Block extends JPanel {

    private Tetromino tetromino;
    private final int blockRow;
    private final int blockColumn;

    public int getBlockRow() {
        return blockRow;
    }

    public void setTetromino(Tetromino tetromino) {
        this.tetromino = tetromino;
    }

    public Tetromino getTetromino() {
        return tetromino;
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
        if (tetromino != null) {
            g2.setColor(tetromino.getColor());
            g2.draw3DRect(widthIncrement, heightIncrement, width, height, true);
            g2.fill3DRect(widthIncrement, heightIncrement, width, height, true);
        }
    }
}
