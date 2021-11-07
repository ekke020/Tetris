package gui.menu;

import gui.menu.game.MenuButton;

import java.util.EventObject;

public class MenuClicks extends EventObject {

    private final int id;
    private MenuButton button;

    public int getId() {
        return id;
    }

    public MenuButton getMenuButton() {
        return button;
    }

    public MenuClicks(Object source, int id) {
        super(source);
        this.id = id;
    }

    public MenuClicks(Object source, int id, MenuButton button) {
        this(source, id);
        this.button = button;
    }
}
