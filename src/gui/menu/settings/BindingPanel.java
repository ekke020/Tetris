package gui.menu.settings;

import colors.Colors;
import gui.menu.MenuButton;
import keybinds.KeybindingLoader;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;

public class BindingPanel extends JPanel {

    private GridBagConstraints gc;
    private Font keyFont;

    private final MenuButton[] buttonList = new MenuButton[5];
    private final JLabel[] keyList = new JLabel[5];

    private final int buttonWidth;
    private final int buttonHeight;

    public BindingPanel(int width, int height) {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(width, height));
        setOpaque(false);

        buttonWidth = width / 2;
        buttonHeight = height / 10;
        keyFont = new Font("Serif", Font.BOLD, width / 15);

        Font titleFont = new Font("Serif", Font.BOLD, width / 15);
        setBorder(BorderFactory.createTitledBorder(
                this.getBorder(),"Keybindings", TitledBorder.LEFT,
                TitledBorder.ABOVE_TOP, titleFont, Colors.FOREGROUND_COLOR));

        justButtons("Move left","MOVE_LEFT", 0);
        justButtons("Move right","MOVE_RIGHT", 1);
        justButtons("Spin","SPIN", 2);
        justButtons("Soft drop","SOFT_DROP", 3);
        justButtons("hard drop","HARD_DROP", 4);
    }

    private void justButtons(String text, String keybinding, int gridy) {
        String key = KeyEvent.getKeyText(KeybindingLoader.getKeybinding(keybinding));
        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = gridy;

        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_START;

        gc.weighty = 0.1;

        MenuButton rebind = new MenuButton(text,buttonWidth, buttonHeight);
        rebind.addActionListener(e -> System.out.println(key));
        buttonList[gridy] = rebind;
        add(rebind, gc);


        gc.gridx = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.weightx = 0.3;
        
        JLabel keybind = new JLabel(key);
        keybind.setFont(keyFont);
        keyList[gridy] = keybind;
        add(keybind, gc);
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        if (this.getBorder() != null) {
            int size = (int) (preferredSize.getWidth() / 15);
            ((javax.swing.border.TitledBorder) getBorder()).setTitleFont(new Font("Serif", Font.BOLD, size));

            int width = (int) (preferredSize.getWidth() / 2);
            int height = (int) (preferredSize.getHeight() / 10);
            for (MenuButton button : buttonList){
                button.setPreferredSize(new Dimension(width, height));
            }
            for (JLabel keybind : keyList) {
                keybind.setFont(new Font("Serif", Font.BOLD, size));
            }
        }

    }
}
