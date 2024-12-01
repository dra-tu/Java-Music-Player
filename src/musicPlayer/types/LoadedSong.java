package musicPlayer.types;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class LoadedSong {
    private Clip clip;
    private String name;
    private int SONG_ID;

    public boolean loadSong(Song song) {
        clip = song.createClip();
        this.name = song.getName();
        this.SONG_ID = song.SONG_ID;
        return clip != null;
    }

    public void start() {
        clip.start();
    }

    public void stop() {
        clip.stop();
    }

    public void exit() {
        clip.close();
    }

    public int getSongId() {
        return SONG_ID;
    }

    public void setTime(long timeMicroseconds) {
        clip.setMicrosecondPosition(timeMicroseconds);
    }

    public long getCurrentTime() {
        return clip.getMicrosecondPosition();
    }

    public long getMaxTime() {
        return clip.getMicrosecondLength();
    }

    public String getName() {
        return name;
    }

    public boolean setVolumePercent(int percent) {
        FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        if (percent > 100f || percent < 0)
            return false;

        float range = control.getMaximum() - control.getMinimum();
        float newVolume = (range * ((float) percent / 100)) + control.getMinimum();

        control.setValue(newVolume);
        return true;
    }
}
