package com.company;

import java.awt.event.KeyEvent;
import java.io.*;
import java.util.HashMap;

public class KeybindingLoader implements java.io.Serializable {

    private static final String KEYBINDINGS = "bindings-cache.txt";

    private static HashMap<String, Integer> keybindings= new HashMap<>();
    private static final HashMap<String, Integer>  defaultKeybindings = new HashMap<>();

    static {
        defaultKeybindings.put("MOVE_LEFT", KeyEvent.VK_LEFT);
        defaultKeybindings.put("MOVE_RIGHT", KeyEvent.VK_RIGHT);
        defaultKeybindings.put("SPIN", KeyEvent.VK_SPACE);
        defaultKeybindings.put("SOFT_DROP", KeyEvent.VK_DOWN);
        defaultKeybindings.put("HARD_DROP", KeyEvent.VK_UP);
        defaultKeybindings.put("PAUSE", KeyEvent.VK_P);
    }

    public static int getKeybinding(String key) {
        return keybindings.get(key);
    }

    @SuppressWarnings("unchecked")
    public static void loadKeybindings() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(KEYBINDINGS))) {
            keybindings = (HashMap<String, Integer>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to read from file....");
            keybindings.putAll(defaultKeybindings);
            try {
                createKeybindings();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void createKeybindings() throws IOException {
        System.out.println("Creating a new " + KEYBINDINGS);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(KEYBINDINGS))){
            out.writeObject(defaultKeybindings);
        }
    }

    public static void saveKeybindings() throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(KEYBINDINGS))){
            out.writeObject(keybindings);
        }
    }

}
