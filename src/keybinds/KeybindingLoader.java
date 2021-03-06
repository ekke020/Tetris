package keybinds;

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
        defaultKeybindings.put("SPIN", KeyEvent.VK_UP);
        defaultKeybindings.put("SOFT_DROP", KeyEvent.VK_DOWN);
        defaultKeybindings.put("HARD_DROP", KeyEvent.VK_SPACE);
        defaultKeybindings.put("PAUSE", KeyEvent.VK_P);
    }

    public static int getKeybinding(String key) {
        return keybindings.get(key);
    }

    public static void setKeybinding(String key, int keyCode) {
        keybindings.put(key, keyCode);
    }

    public static boolean keybindingContains(int keyCode) {
        return keybindings.containsValue(keyCode);
    }

    @SuppressWarnings("unchecked")
    public static void loadKeybindings() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(KEYBINDINGS))) {
            keybindings = (HashMap<String, Integer>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to read from file....");
            keybindings.putAll(defaultKeybindings);
            createKeybindings();
        }
    }

    private static void createKeybindings() {
        System.out.println("Creating a new " + KEYBINDINGS);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(KEYBINDINGS))){
            out.writeObject(defaultKeybindings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveKeybindings() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(KEYBINDINGS))){
            out.writeObject(keybindings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
