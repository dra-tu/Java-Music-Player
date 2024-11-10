package MusicPlayer;

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

    public String getName() {
        return name;
    }

    public TimeStamp getTimeStampPosition() {
        return new TimeStamp(clip.getMicrosecondPosition());
    }

    public TimeStamp getTimeStampLength() {
        return new TimeStamp(clip.getMicrosecondLength());
    }
}
