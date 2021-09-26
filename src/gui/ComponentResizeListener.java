package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public abstract class ComponentResizeListener extends ComponentAdapter implements ActionListener {

    private final Timer timer;

    public ComponentResizeListener() {
        this(200);
    }

    public ComponentResizeListener(int delayMS) {
        timer = new Timer(delayMS, this);
        timer.setRepeats(false);
        timer.setCoalesce(false);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        timer.restart();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        resizeTimedOut();
    }

    public abstract void resizeTimedOut();

}
