package MusicPlayer.Types;

import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;

public class Song {
    File file;
    String name;
    final int SONG_ID;

    public Song(int SONG_ID) {
        this.SONG_ID = SONG_ID;
    }

    public String getName() {
        return name;
    }

    public int getSongId() {
        return SONG_ID;
    }

    public boolean setFile(File file) {
        if( !file.isFile() || !file.getName().endsWith(".wav") ) return false;

        this.file = file;
        this.name = file.getName().replaceAll(".wav", "");

        return true;
    }

    Clip createClip() {
        try {

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            audioStream.close();

            return clip;

        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            return null;
        }
    }
}
