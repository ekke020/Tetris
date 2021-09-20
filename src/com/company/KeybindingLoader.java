package com.company;

import java.awt.event.KeyEvent;
import java.io.*;

public class KeybindingLoader {

    private static final String KEYBINDINGS = "bindings-cache.txt";
    public final static int MOVE_LEFT = KeyEvent.VK_LEFT;
    public final static int MOVE_RIGHT = KeyEvent.VK_RIGHT;
    public final static int SPIN = KeyEvent.VK_SPACE;
    public final static int SOFT_DROP = KeyEvent.VK_DOWN;
    public final static int HARD_DROP = KeyEvent.VK_UP;

    private static void loadKeybindings() throws IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(KEYBINDINGS))) {
            String key = reader.readLine();
        }
    }

    private static void createKeybindings() throws IOException {

        try (FileWriter bindings = new FileWriter(KEYBINDINGS)) {
            bindings.write(KEYBINDINGS);
        }
    }
}
