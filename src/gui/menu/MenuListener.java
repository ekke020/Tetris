package gui.menu;

import java.util.EventListener;

public interface MenuListener extends EventListener {
    void formEventOccurred(MenuClicks e);
}
