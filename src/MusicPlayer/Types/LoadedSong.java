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

    public void start() {
        clip.start();
    }

    public void stop() {
        clip.stop();
    }

    public int getSongId() {
        return SONG_ID;
    }

    public void setTime(TimeStamp time) {
        clip.setMicrosecondPosition(time.toMicroseconds());
    }

    public void setTimeToStart() {
        clip.setMicrosecondPosition(0L);
    }

    public TimeStamp getCurrentTime() {
        return new TimeStamp(clip.getMicrosecondPosition());
    }

    public TimeStamp getMaxTime() {
        return new TimeStamp(clip.getMicrosecondLength());
    }

    public String getName() {
        return name;
    }
}
