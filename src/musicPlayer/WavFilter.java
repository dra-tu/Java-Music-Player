package musicPlayer;

import java.io.File;
import java.io.FilenameFilter;

public class WavFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String name) {
        if (new File(dir, name).isDirectory())
            return false;
        return name.endsWith(".wav");
    }
}
