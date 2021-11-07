package gui.menu.settings;


import javax.swing.*;
import java.awt.*;

public class SelectedOptionPanel extends JPanel {

    private ReturnListener returnListener;
    private final SoundSliderPanel soundSliderPanel = new SoundSliderPanel();
    private final LevelSelectPanel levelSelectPanel = new LevelSelectPanel();
    private final BindingPanel bindingPanel = new BindingPanel();
    private final StartGamePanel startGamePanel = new StartGamePanel();
    private final JPanel[] panels = {
            bindingPanel, soundSliderPanel, levelSelectPanel, startGamePanel
    };
    private int index = 0;

    public SelectedOptionPanel() {
        setOpaque(false);
        setLayout(new BorderLayout());
        addBindingPanel();
    }

    public void addBindingPanel() {
        index = 0;
        removeAll();
        add(bindingPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void addSoundSliderPanel() {
        index = 1;
        removeAll();
        add(soundSliderPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void addLevelSelectPanel() {
        index = 2;
        removeAll();
        add(levelSelectPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void addStartGamePanel() {
        index = 3;
        removeAll();
        add(startGamePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public int getSelectedLevel() {
        return levelSelectPanel.getLevel();
    }

    public void focusPanel() {
        panels[index].requestFocusInWindow();
    }

    public void addListenerToPanels() {
        soundSliderPanel.setReturnListener(returnListener);
        levelSelectPanel.setReturnListener(returnListener);
        bindingPanel.setReturnListener(returnListener);
    }

    public void addReturnListener(ReturnListener listener) {
        this.returnListener = listener;
    }
}
