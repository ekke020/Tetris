package gui.menu.settings;

import gui.ComponentResizeListener;
import keybinds.KeyBinder;
import keybinds.KeybindingLoader;
import staticAssets.Colors;
import staticAssets.Fonts;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class BindingPanel extends JPanel implements FocusListener {

    private ReturnListener returnListener;
    private final KeyPanel[] panels = new KeyPanel[5];
    private int index = 0;

    public BindingPanel() {
        setLayout(new GridLayout(5, 1));
        setOpaque(false);
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(),"KEYS", TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION, Fonts.getFont(Fonts.TEXT_FONT).deriveFont(Font.PLAIN, 19F), Colors.FOREGROUND_COLOR));

        panels[0] = new KeyPanel("Move left","MOVE_LEFT");
        panels[1] = new KeyPanel("Move right","MOVE_RIGHT");
        panels[2] = new KeyPanel("Spin","SPIN");
        panels[3] = new KeyPanel("Soft drop","SOFT_DROP");
        panels[4] = new KeyPanel("hard drop","HARD_DROP");
        add(panels[0]);
        add(panels[1]);
        add(panels[2]);
        add(panels[3]);
        add(panels[4]);

        addFocusListener(this);
        addComponentListener(new ComponentResizeListener() {
            @Override
            public void resizeTimedOut() {
                setComponentSizes();
            }
        });
    }

    @Override
    public void focusGained(FocusEvent e) {
        panels[index].setFocus();
        KeyBinder.addKeyBinding(this, KeyEvent.VK_DOWN, "DOWN", true, f -> {
            changeFocus(1);
        });
        KeyBinder.addKeyBinding(this, KeyEvent.VK_UP, "UP", true, f -> {
            changeFocus(-1);
        });
        KeyBinder.addKeyBinding(this, KeyEvent.VK_ESCAPE, "BACK", true, f -> {
            returnListener.returnEventOccurred();
        });
        KeyBinder.addKeyBinding(this, KeyEvent.VK_ENTER, "ENTER", true, f -> {
            new KeyInputDialog(this, panels[index].keybinding, panels[index].keybindingLabel);
        });
    }

    @Override
    public void focusLost(FocusEvent e) {
        panels[index].removeFocus();
        KeyBinder.removeKeyBinding(this, KeyEvent.VK_DOWN, true);
        KeyBinder.removeKeyBinding(this, KeyEvent.VK_UP, true);
        KeyBinder.removeKeyBinding(this, KeyEvent.VK_ESCAPE, true);
        KeyBinder.removeKeyBinding(this, KeyEvent.VK_ENTER, true);
    }

    private void setComponentSizes() {
        int size =  (getWidth() / 20);
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(),"KEYS", TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION, Fonts.getFont(Fonts.TEXT_FONT).deriveFont(Font.PLAIN,
                        size), Colors.FOREGROUND_COLOR));
        for (KeyPanel panel : panels) {
            panel.setFontSizes();
        }
        SwingUtilities.updateComponentTreeUI(this);
    }

    private void changeFocus(int num) {
        panels[index].removeFocus();
        index += num;
        if (index > 4) {
            index = 0;
        }
        else if (index < 0) {
            index = 4;
        }
        panels[index].setFocus();
        repaint();
    }

    public void setReturnListener(ReturnListener listener) {
        returnListener = listener;
    }

    private static class KeyPanel extends JPanel {

        private final String keybinding;
        private JLabel keybindingLabel;
        private JLabel textLabel;
        private Font font;

        public KeyPanel(String text, String keybinding) {
            this.keybinding = keybinding;
            font = Fonts.getFont(Fonts.TEXT_FONT).deriveFont(Font.PLAIN, 15F);
            setOpaque(false);
            setLayout(new GridLayout(1,2));
            String key = KeyEvent.getKeyText(KeybindingLoader.getKeybinding(keybinding));
            addText(text);
            addKeyBinding(key);
        }

        private void addText(String text) {
            textLabel = new JLabel(text, SwingConstants.LEADING);
            textLabel.setFont(font);
            textLabel.setForeground(Colors.FOREGROUND_COLOR);
            add(textLabel);
        }

        private void addKeyBinding(String text) {
            keybindingLabel = new JLabel(text, SwingConstants.TRAILING);

            keybindingLabel.setFont(font);
            keybindingLabel.setForeground(Colors.FOREGROUND_COLOR);
            add(keybindingLabel);
        }

        private void setFocus() {
            setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR));
        }

        private void removeFocus() {
            setBorder(BorderFactory.createEmptyBorder());
        }

        private void setFontSizes() {
            int size = (getWidth() / 25);
            font = font.deriveFont(Font.PLAIN, size);
            textLabel.setFont(font);
            keybindingLabel.setFont(font);
        }
    }
}