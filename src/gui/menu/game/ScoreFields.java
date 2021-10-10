package gui.menu.game;

import javax.swing.*;
import java.awt.*;

public class ScoreFields extends JPanel {

    private final TextField[] textFields = new TextField[6];


    public ScoreFields() {
        setLayout(new GridLayout(3, 2));
        setOpaque(false);

        textFields[0] = new TextField("LEVEL", 4, 2, 4, 0, SwingConstants.LEFT);
        add(textFields[0]);
        textFields[1] = new TextField("0", 4, 2, 0, 4, SwingConstants.RIGHT);
        add(textFields[1]);

        textFields[2] = new TextField("SCORE", 2, 2, 4, 0, SwingConstants.LEFT);
        add(textFields[2]);
        textFields[3] = new TextField("0", 2, 2, 0, 4, SwingConstants.RIGHT);
        add(textFields[3]);

        textFields[4] = new TextField("LINES", 2, 4, 4, 0, SwingConstants.LEFT);
        add(textFields[4]);
        textFields[5] = new TextField("0", 2, 4, 0, 4, SwingConstants.RIGHT);
        add(textFields[5]);
    }

    public void updateFields(int level, int score, int lines) {
        textFields[1].setText(String.valueOf(level));
        textFields[3].setText(String.valueOf(score));
        textFields[5].setText(String.valueOf(lines));
    }
    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        System.out.println(getWidth());
        for (TextField textField : textFields) {
//            textField.setColumns();
        }
    }
}
