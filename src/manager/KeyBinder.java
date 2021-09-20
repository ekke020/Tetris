package manager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KeyBinder {

    public static void addKeyBinding(JComponent comp, int keyCode, String id, boolean onKeyRelease, ActionListener actionListener) {
        InputMap inMap = comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap ap = comp.getActionMap();
        inMap.put(KeyStroke.getKeyStroke(keyCode, 0, onKeyRelease), id);
        ap.put(id, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListener.actionPerformed(e);
            }
        });
    }

    public static void removeKeyBinding(JComponent comp, int keyCode, String id, boolean onKeyRelease) {
        InputMap inMap = comp.getInputMap(JComponent.WHEN_FOCUSED);
        inMap.put(KeyStroke.getKeyStroke(keyCode, 0, onKeyRelease), id);
    }
}
