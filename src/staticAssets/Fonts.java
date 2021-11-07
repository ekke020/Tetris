package staticAssets;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Fonts {

    private static final Path MAIN_MENU_FONT_PATH = Path.of("assets/Fonts/Main_Menu_Font.otf");
    private static final Path TEXT_FONT_PATH = Path.of("assets/Fonts/Miscellaneous_Text_Font.ttf");
    private static final List<Font> FONTS_LIST = new ArrayList<>();
    public static final int MAIN_MENU_FONT = 0;
    public static final int TEXT_FONT = 1;

    static {
        loadFonts();
    }

    public static Font getFont(int font) {
        return FONTS_LIST.get(font);
    }

    private static void loadFonts() {
        try {
            FONTS_LIST.add(
                    Font.createFont(Font.TRUETYPE_FONT, MAIN_MENU_FONT_PATH.toFile()).deriveFont(Font.PLAIN,200)
            );
            FONTS_LIST.add(Font.createFont(
                    Font.TRUETYPE_FONT, TEXT_FONT_PATH.toFile()).deriveFont(Font.PLAIN,20)
            );
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
