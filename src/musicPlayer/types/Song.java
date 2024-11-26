package musicPlayer.types;

import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;

public class Song {
    private File file;
    private String name;
    public final int SONG_ID;

    public Song(int SONG_ID) {
        this.SONG_ID = SONG_ID;
    }

    public String getName() {
        return name;
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

            try {
                clip.open(audioStream);
            }catch (IllegalArgumentException _) {
                return null;
            }


            audioStream.close();

            return clip;

        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            return null;
        }
    }
}
