package gui.menu.settings;

import keybinds.KeyBinder;
import sound.AudioPlayer;
import staticAssets.Colors;
import staticAssets.Fonts;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

public class SoundSliderPanel extends JPanel implements FocusListener {

    private ReturnListener returnListener;
    private final Slider[] sliders = new Slider[2];
    private int index = 0;

    public SoundSliderPanel() {
        setOpaque(false);
        setLayout(new GridLayout(2,1));
        sliders[0] = new Slider("Sound effects", AudioPlayer.getMiscVolume(), 0);
        add(sliders[0]);
        sliders[1] = new Slider("Background music", AudioPlayer.getBackgroundVolume(), 1);
        add(sliders[1]);

        addFocusListener(this);

    }

    @Override
    public void focusGained(FocusEvent e) {
        KeyBinder.addKeyBinding(this, KeyEvent.VK_DOWN, "DOWN", true, f -> {
            switchFocus();
        });
        KeyBinder.addKeyBinding(this, KeyEvent.VK_UP, "UP", true, f -> {
            switchFocus();
        });
        KeyBinder.addKeyBinding(this, KeyEvent.VK_LEFT, "LEFT", false, f -> {
            sliders[index].adjustSoundLevel(false);
        });
        KeyBinder.addKeyBinding(this, KeyEvent.VK_RIGHT, "RIGHT", false, f -> {
            sliders[index].adjustSoundLevel(true);
        });
        KeyBinder.addKeyBinding(this, KeyEvent.VK_ESCAPE, "BACK", true, f -> {
            returnListener.returnEventOccurred();
        });
        sliders[index].hasFocus = true;
        repaint();
    }

    @Override
    public void focusLost(FocusEvent e) {
        KeyBinder.removeKeyBinding(this, KeyEvent.VK_DOWN, true);
        KeyBinder.removeKeyBinding(this, KeyEvent.VK_UP, true);
        KeyBinder.removeKeyBinding(this, KeyEvent.VK_LEFT, false);
        KeyBinder.removeKeyBinding(this, KeyEvent.VK_RIGHT, false);
        KeyBinder.removeKeyBinding(this, KeyEvent.VK_ESCAPE, true);
        sliders[index].hasFocus = false;
    }

    private void switchFocus() {
        sliders[index].hasFocus = false;
        index++;
        if (index > 1)
            index = 0;
        sliders[index].hasFocus = true;
        repaint();
    }

    public void setReturnListener(ReturnListener listener) {
        returnListener = listener;
    }


    private static class Slider extends JPanel {

        private final String title;
        private Font font;
        private float soundLevel;
        private boolean hasFocus = false;
        private final int id;

        Slider(String title, float soundLevel, int id) {
            this.title = title;
            this.id = id;
            this.soundLevel = soundLevel;
            System.out.println(getWidth());
            setOpaque(false);
            setLayout(new GridLayout(1,1));
            font = Fonts.getFont(Fonts.TEXT_FONT).deriveFont(Font.PLAIN, 8F);
            setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), title,
                    TitledBorder.LEFT, TitledBorder.ABOVE_TOP, font, Colors.FOREGROUND_COLOR));

        }

        private void adjustSoundLevel(boolean higher) {
            if (higher) {
                if (soundLevel == 1f) {
                    return;
                }
                soundLevel += 0.01f;
            }
            else {
                if (soundLevel == 0f) {
                    return;
                }
                soundLevel -= 0.01f;
            }
            soundLevel = (float) (Math.round(soundLevel * 100.0) / 100.0);
            AudioPlayer.updateVolumes(soundLevel * 100, id);
            repaint();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            int size = (getWidth() / 40);
            font = font.deriveFont(Font.PLAIN, size);
            setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), title,
                    TitledBorder.LEFT, TitledBorder.ABOVE_TOP, font, Colors.FOREGROUND_COLOR));

            int x = 5;
            int y = (getHeight() / 8) + size;
            int width = getWidth() - (x + 1);
            int height = getHeight() / 8;
            if (hasFocus)
                g2.setStroke(new BasicStroke(2f));
            g2.setColor(Colors.BORDER_COLOR);
            g2.drawRect(x, y, width, height);

            int ovalWidth = width / 80;
            double position = (soundLevel * 100) / (width + ovalWidth);
            position *= width;
            double ovalPosition = (((double) width / 100) * Math.round(position));
            int position2 = (int) Math.round(ovalPosition);

            g2.setColor(Colors.FOREGROUND_COLOR);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.fillOval(position2 + ovalWidth, y, ovalWidth, height);

        }

    }
}
