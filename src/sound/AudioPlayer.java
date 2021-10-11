package sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class AudioPlayer {

    private Clip clip;
    private final boolean isLoop;
    private final Path path;
    private final float volume;

    public AudioPlayer(Path path, boolean isLoop, float volume) {
        this.isLoop = isLoop;
        this.path = path;
        this.volume = volume;
    }

    public void play() {
        try (AudioInputStream input = AudioSystem.getAudioInputStream(new File(path.toString()))) {
            clip = AudioSystem.getClip();
            clip.open(input);
            setVolume(volume);
            clip.start();
            if (isLoop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void pause() {
        clip.stop();
    }

    public void start() {
        clip.start();
        if (isLoop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void close() {
        clip.close();
    }

    private void setVolume(float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

}
