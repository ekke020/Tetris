package gui.menu.game;

import colors.Colors;
import gui.Block;
import tetromino.*;

import javax.swing.*;
import java.awt.*;

public class Showcase extends JPanel {

    private Block[][] windowBlocks;

    public Showcase(int width, int height) {
        setLayout(new GridLayout(4, 6));
        setPreferredSize(new Dimension(width, height));
        setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR, 2));
        setBackground(Colors.MISC_BACKGROUND);
        createBoard();
    }

    private void createBoard() {
        windowBlocks = new Block[4][6];

        for (int i = 0; i < windowBlocks.length; i++) {
            for (int j = 0; j < windowBlocks[i].length; j++) {
                windowBlocks[i][j] = new Block(i, j);
                add(windowBlocks[i][j]);
            }
        }
    }

    public void updateShowcase(Tetromino tetromino) {
        removeOldShowCase();
        for (int[] coordinates : tetromino.getCoordinates()) {
            windowBlocks[coordinates[0] + 1][coordinates[1] - 3].setTetromino(tetromino);
        }
    }

    private void removeOldShowCase() {
        for (Block[] blocks : windowBlocks) {
            for (Block block : blocks) {
                block.setTetromino(null);
            }
        }
    }
}
