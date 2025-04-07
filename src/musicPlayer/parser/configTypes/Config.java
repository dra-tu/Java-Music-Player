package musicPlayer.parser.configTypes;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class Config extends FileSaver {
    private Integer defaultVolumePercent;
    private final static int startVolumePercent = 100;
    protected static Field[] ignoreFields;

    static {
        try {
            addToIgnoreFields(new Field[]{
                    Config.class.getDeclaredField("startVolumePercent"),
                    Config.class.getDeclaredField("ignoreFields")
            });
        } catch (NoSuchFieldException e) {
            ignoreFields = FileSaver.ignoreFields;
        }
    }


    public Config(File saveFile) throws FileSaverException, IOException {
        super(saveFile);
    }

    public Config(File saveFile, int defaultVolumePercent) throws IOException {
        super(saveFile);
        setDefaultVolumePercent(defaultVolumePercent);
    }

    public int getDefaultVolumePercent() {
        return defaultVolumePercent == null
                ? startVolumePercent
                : defaultVolumePercent;
    }

    public boolean setDefaultVolumePercent(int volumePercent) throws IOException {
        return updateField(
                "defaultVolumePercent",
                (volPer) -> volPer <= 100 && volPer >= 0,
                volumePercent
        );
    }
}
