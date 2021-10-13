package sound;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum SoundPaths {

    GAME_OVER_SOUND("assets/sound/Game_Over.wav"),
    TETRIS_GAME_SOUND("assets/sound/Tetris_Game_Sound.wav"),
    TETRIS_MENU_SOUND("assets/sound/Tetris_Menu_Sound.wav"),
    PAUSE_SOUND("assets/sound/Pause.wav"),
    TETRIS_SOUND("assets/sound/Tetris.wav"),
    LINE_CLEARED_SOUND("assets/sound/Line_Cleared.wav"),
    TETROMINO_LANDING_SOUND("assets/sound/Tetromino_Landing.wav"),
    TETROMINO_MOVE_SOUND("assets/sound/Tetromino_Move.wav"),
    TETROMINO_MOVE_DENIED_SOUND("assets/sound/Tetromino_Move_Denied.wav"),
    TETROMINO_SPIN_SOUND("assets/sound/Tetromino_Spin.wav"),
    LEVEL_UP_SOUND("assets/sound/Level_Up.wav");

    private final Path path;

    public Path getPath() {
        return path;
    }

    SoundPaths(String path) {
        this.path = Paths.get(path);
    }

}
