package MusicPlayer;

import java.io.File;
import java.io.FilenameFilter;

public class WavFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String name) {
        return name.endsWith(".wav");
    }
}
