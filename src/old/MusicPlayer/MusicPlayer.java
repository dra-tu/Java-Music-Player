package old.MusicPlayer;

import old.Types.Song;
import old.Types.Time;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.io.IOException;

import java.util.*;

public class MusicPlayer {
    Song[] songs;
    final File rootDir;

    // for mixed Playback
    ArrayList<Song> toPlay;

    // statistics
    int songsPlayed;
    int currentSongNum;

    MusicPlayerControls musicPlayerControls;

    // constructors
    public MusicPlayer(String rootDirPath) throws LineUnavailableException, MyException, UnsupportedAudioFileException, IOException {
        this.rootDir = new File(rootDirPath);
        if (!this.rootDir.isDirectory()) {
            throw new MyException("Not a Directory");
        }

        this.musicPlayerControls = new MusicPlayerControls(this);

        musicPlayerControls.loadSongs();

        this.toPlay = new ArrayList<>();

        this.songsPlayed = 0;
        this.currentSongNum = -1;
    }

    public Song getCurrentSong() {
        return songs[currentSongNum];
    }

    public MusicPlayerControls getControls() {
        return musicPlayerControls;
    }

    public int getSongsLength() {
        return songs.length;
    }

    public String getSongName(int index) {
        return songs[index].name;
    }

    public Time getCurrentTime() {
        return Time.fromMicroseconds(getCurrentSong().getClip().getMicrosecondPosition());
    }

    public void setSongPosition(Time time) {
        getCurrentSong().setTimePosition(time);
    }

}