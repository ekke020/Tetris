package gui.menu.game;

import colors.Colors;
import gui.menu.MenuButton;
import gui.menu.MenuClicks;
import gui.menu.MenuListener;
import keybinds.KeybindingLoader;
import keybinds.KeyBinder;
import tetromino.Tetromino;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// TODO: MAKE THE MENU DYNAMIC. -> (MAINMENU)

public class GameMenu extends JPanel {

    private Showcase showcase;
    private ScoreFields fields;
    private GridBagConstraints gc;
    private MenuListener menuListener;
    private final MenuButton[] buttonList = new MenuButton[2];

    public GameMenu(int width, int height) {

        setPreferredSize(new Dimension(width, height));
        setBackground(Colors.BACKGROUND_COLOR);
        addBorder();
        setLayout(new GridBagLayout());
        addTetrominoWindow();
        addScoreField();
        addButtonOne((int) (width * 0.90), height / 16);
        addButtonTwo((int) (width * 0.90), height / 16);

    }

    private void addBorder() {
        Border raisedBevel = BorderFactory.createSoftBevelBorder(BevelBorder.RAISED,
                Colors.BORDER_COLOR, Colors.BORDER_COLOR);
        Border loweredBevel = BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED,
                Colors.BORDER_COLOR, Colors.BORDER_COLOR);

        Border compound = BorderFactory.createCompoundBorder(
                raisedBevel, loweredBevel);
        setBorder(compound);
    }

    private void addTetrominoWindow() {
        gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 0;
        gc.weighty = 0;
        gc.ipady = 100;
        gc.insets = new Insets(10, 40, 10, 40);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.fill = GridBagConstraints.HORIZONTAL;
        showcase = new Showcase();
        add(showcase, gc);
    }

    private void addScoreField() {
        gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 1;
        gc.weightx = 0;
        gc.weighty = 0.5;
        gc.ipady = 0;
//        gc.insets = new Insets(10, 10, 0, 10);
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.NONE;
        fields = new ScoreFields();
        add(fields, gc);
    }

    private void addButtonOne(int width, int height) {
        MenuButton button = new MenuButton("Pause", width, height);
        KeyBinder.addKeyBinding(button, KeybindingLoader.getKeybinding("PAUSE"), "pause", true, e -> {
            MenuClicks mc = new MenuClicks(this, 0, (MenuButton) e.getSource());
            menuListener.formEventOccurred(mc);
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuClicks mc = new MenuClicks(this, 0, (MenuButton) e.getSource());
                menuListener.formEventOccurred(mc);
            }
        });
        gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 2;
        gc.weightx = 0;
        gc.weighty = 0;
        gc.ipady = 20;
//        gc.insets = new Insets(10, 0, 0, 0);
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.NONE;
        buttonList[0] = button;
        add(button, gc);

    }

    private void addButtonTwo(int width, int height) {
        MenuButton button = new MenuButton("Exit", width, height);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuClicks mc = new MenuClicks(this, 1);
                menuListener.formEventOccurred(mc);
            }
        });
        gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 3;
        gc.weightx = 0;
        gc.weighty = 0.09;
        gc.ipady = 20;
//        gc.insets = new Insets(10, 10, 0, 10);
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.NONE;
        buttonList[1] = button;
        add(button, gc);
    }

    public void setMenuListener(MenuListener listener) {
        this.menuListener = listener;
    }

    public void updateMenu(Tetromino tetromino, int level, int score, int lines) {
        showcase.updateShowcase(tetromino);
        showcase.repaint();
        fields.updateFields(level, score, lines);
        fields.repaint();
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        if (buttonList[0] != null) {
            int width = (int) (preferredSize.getWidth() * 0.9);
            int height = (int) (preferredSize.getHeight() / 16);
            for (MenuButton button : buttonList){
                button.setPreferredSize(new Dimension(width, height));
            }
//            fields.setPreferredSize(new Dimension());
        }
    }
}
