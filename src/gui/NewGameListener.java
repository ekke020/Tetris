package gui;

import java.util.EventListener;

public interface NewGameListener extends EventListener {

    void newGameEventOccurred(int level);
}
