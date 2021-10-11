package sound;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum SoundPaths {

    GAME_OVER("assets/sound/Game_Over.wav", 0.2f),
    TETRIS_GAME_SOUND("assets/sound/Tetris_Game_Sound.wav", 0.2f),
    TETRIS_MENU_SOUND("assets/sound/Tetris_Menu_Sound.wav", 0.2f),
    PAUSE("assets/sound/Pause.wav", 0.6f),
    TETRIS("assets/sound/Tetris.wav", 0.6f),
    LINE_CLEARED("assets/sound/Line_Cleared.wav", 0.6f),
    TETROMINO_LANDING("assets/sound/Tetromino_Landing.wav", 0.6f),
    TETROMINO_MOVE("assets/sound/Tetromino_Move.wav", 0.6f),
    TETROMINO_MOVE_DENIED("assets/sound/Tetromino_Move_Denied.wav", 0.6f),
    TETROMINO_SPIN("assets/sound/Tetromino_Spin.wav", 0.6f),
    LEVEL_UP("assets/sound/Level_Up.wav", 0.6f);

    private final Path path;
    public final float volume;

    public Path getPath() {
        return path;
    }

    public float getVolume() {
        return volume;
    }

    SoundPaths(String path, float volume) {
        this.path = Paths.get(path);
        this.volume = volume;
    }

}
