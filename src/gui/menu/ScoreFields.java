package gui.menu;

import javax.swing.*;
import java.awt.*;

public class ScoreFields extends JPanel {


    private final TextField levelText;
    private final TextField scoreText;
    private final TextField linesText;

    public ScoreFields() {
        setLayout(new GridLayout(3, 2));
        setOpaque(false);

        add(new TextField("LEVEL", 4, 2, 4, 0, SwingConstants.LEFT));
        levelText = new TextField("0", 4, 2, 0, 4, SwingConstants.RIGHT);
        add(levelText);

        add(new TextField("SCORE", 2, 2, 4, 0, SwingConstants.LEFT));
        scoreText = new TextField("0", 2, 2, 0, 4, SwingConstants.RIGHT);
        add(scoreText);

        add(new TextField("LINES", 2, 4, 4, 0, SwingConstants.LEFT));
        linesText = new TextField("0", 2, 4, 0, 4, SwingConstants.RIGHT);
        add(linesText);
    }

    public void updateFields(int level, int score, int lines) {
        levelText.setText(String.valueOf(level));
        scoreText.setText(String.valueOf(score));
        linesText.setText(String.valueOf(lines));
    }
}
