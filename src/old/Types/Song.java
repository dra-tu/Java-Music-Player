package old.Types;

import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;

public class Song {
    String name;
    int number;
    Clip clip;

    // constructors
    Song(String path, int number) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this(
                new File(path),
                AudioSystem.getAudioInputStream(new File(path)),
                number
        );
    }

    Song(File file, int number) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this(
                file,
                AudioSystem.getAudioInputStream(file),
                number
        );
    }

    Song(File file, AudioInputStream audio, int number) throws LineUnavailableException, IOException {
        // TODO: Remove file ending from name example: example.wav => example
        this.name = file.getName();
        this.clip = AudioSystem.getClip();
        this.clip.open(audio);
    }

    // getters
    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Clip getClip() {
        return clip;
    }

    public void setTimePosition(Time time) {
        clip.setMicrosecondPosition();
    }
}
