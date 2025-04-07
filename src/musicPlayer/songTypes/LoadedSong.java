package musicPlayer.songTypes;

import musicPlayer.parser.configTypes.SongConfig;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class LoadedSong {
    private Clip clip;
    private Song song;
    private SongConfig config;

    public boolean loadSong(Song song, File confDir) throws IOException {
        this.song = song;
        this.clip = song.createClip();

        if (clip == null) return false;

        this.config = new SongConfig(Path.of(confDir.getAbsolutePath(), song.getName()).toFile());
        setRealVolume(config.getVolumePercent());
        return true;
    }

    public void start() {
        clip.start();
    }

    public void stop() {
        clip.stop();
    }

    public void exit() {
        stop();
        clip.close();
    }

    public int getSongId() {
        return song.SONG_ID;
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
        return song.getName();
    }

    private boolean setRealVolume(int percent) {
        if (percent > 100 || percent < 0)
            return false;

        FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        float range = control.getMaximum() - control.getMinimum();
        float newVolume = (range * ((float) percent / 100)) + control.getMinimum();

        control.setValue(newVolume);
        return true;
    }

    /// @param percent from 0 to 100
    public boolean setVolumePercent(int percent) throws IOException {
        int oldVolume = config.getVolumePercent();
        boolean ValueSet = config.setVolumePercent(percent);

        if (!ValueSet) return false;

        ValueSet = setRealVolume(percent);
        if (!ValueSet) {
            if (!config.setVolumePercent(oldVolume))
                throw new IOException();
            return false;
        }

        return true;
    }

    public int getVolumePercent() {
        return config.getVolumePercent();
    }
}
