package sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    private static Clip clip;

    /*
     * Early test of implementing sound.
     */
    public static void play() {
        try {
            AudioInputStream input = AudioSystem.getAudioInputStream(new File("assets/sound/Tetris_Game_Sound.wav"));
            clip=AudioSystem.getClip();
            clip.open(input);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
