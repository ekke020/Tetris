package sound;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;

import static sound.SoundPaths.*;

public class AudioPlayer {

    private static final HashMap<Integer, byte[]> soundMap = new HashMap<>();
    private static Clip backgroundMusic;
    private static float BACKGROUND_VOLUME;
    private static float MISC_VOLUME;

    public static final int GAME = 0;
    public static final int MENU = 1;

    public static final int PAUSE = 2;
    public static final int GAME_OVER = 3;

    public static final int MOVE = 4;
    public static final int MOVE_DENIED = 5;
    public static final int SPIN = 6;
    public static final int TETROMINO_LANDING = 7;

    public static final int TETRIS = 8;
    public static final int LINE_CLEARED = 9;
    public static final int LEVEL_UP = 10;

    static {
        soundMap.put(0, loadClipByteArray(TETRIS_GAME_SOUND.getPath()));
        soundMap.put(1, loadClipByteArray(TETRIS_MENU_SOUND.getPath()));
        soundMap.put(2, loadClipByteArray(PAUSE_SOUND.getPath()));
        soundMap.put(3, loadClipByteArray(GAME_OVER_SOUND.getPath()));
        soundMap.put(4, loadClipByteArray(TETROMINO_MOVE_SOUND.getPath()));
        soundMap.put(5, loadClipByteArray(TETROMINO_MOVE_DENIED_SOUND.getPath()));
        soundMap.put(6, loadClipByteArray(TETROMINO_SPIN_SOUND.getPath()));
        soundMap.put(7, loadClipByteArray(TETROMINO_LANDING_SOUND.getPath()));
        soundMap.put(8, loadClipByteArray(TETRIS_SOUND.getPath()));
        soundMap.put(9, loadClipByteArray(LINE_CLEARED_SOUND.getPath()));
        soundMap.put(10, loadClipByteArray(LEVEL_UP_SOUND.getPath()));
    }

    public static void play(int sound) {
        Clip clip = getClipFromByteArray(soundMap.get(sound));
        if (clip == null) {
            return;
        }
        if (sound == 0 || sound == 1) {
            switchBackgroundMusic(clip);
            setVolume(clip, getVolume(sound));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            setVolume(clip, getVolume(sound));
        }
        clip.start();
    }

    private static float getVolume(int sound) {

        return 0;
    }

    private static void switchBackgroundMusic(Clip sound) {
        if (backgroundMusic != null) {
            backgroundMusic.close();
        }
        backgroundMusic = sound;
    }

    public static void pauseBackgroundMusic() {
        backgroundMusic.stop();
    }

    public static void resumeBackgroundMusic() {
        backgroundMusic.start();
        backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
    }

    private static byte[] loadClipByteArray(Path path) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try(BufferedInputStream in = new BufferedInputStream(new FileInputStream(path.toFile()))) {
            int read;
            byte[] buff = new byte[1024];
            while ((read = in.read(buff)) > 0)
            {
                out.write(buff, 0, read);
            }
            out.flush();
            System.out.println(Arrays.toString(out.toByteArray()));
            return out.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Clip getClipFromByteArray(byte[] clipByte) {
        try (AudioInputStream input = AudioSystem.getAudioInputStream(new ByteArrayInputStream(clipByte))) {
            DataLine.Info info = new DataLine.Info(Clip.class, input.getFormat());
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(input);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateVolumes(float volume, int slider) {
        if (slider == 0) {
            setVolume(backgroundMusic, volume / 100);
        } else if (slider == 1) {

        }
    }
    //TODO: Fix the volume of each Clip
    private static void setVolume(Clip clip, float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

}
