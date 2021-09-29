package gui.menu.game;

import gui.Block;
import tetromino.*;

import javax.swing.*;
import java.awt.*;

public class Showcase extends JPanel {

    private Block[][] windowBlocks;

    public Showcase() {
        setLayout(new GridLayout(4, 6));
        Color borderColor = new Color(91, 228, 247, 97);
        setBorder(BorderFactory.createLineBorder(borderColor, 2));
        setBackground(new Color(5, 226, 255, 255));
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
