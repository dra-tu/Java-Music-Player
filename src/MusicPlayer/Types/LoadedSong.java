package MusicPlayer.Types;

import javax.sound.sampled.Clip;

public class LoadedSong {
    Clip clip;
    String name;
    int SONG_ID;

    public boolean loadSong(Song song) {
        clip = song.createClip();
        this.name = song.name;
        this.SONG_ID = song.SONG_ID;
        return clip != null;
    }

    public void unload() {
        clip.stop();
        clip.close();
    }

    public void start() {
        clip.start();
    }

    public void stop() {
        clip.stop();
    }

    public int getSongId() {
        return SONG_ID;
    }

    public void setTime(long timeMicroseconds) {
        clip.setMicrosecondPosition(timeMicroseconds);
    }

    public void setTimeToStart() {
        setTime(0L);
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
}
