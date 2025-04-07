package musicPlayer.parser.configTypes;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class SongConfig extends FileSaver {
    private static Config globalConfig;
    private Integer volumePercent;
    protected static Field[] ignoreFields;

    static {
        try {
            addToIgnoreFields(new Field[]{
                    SongConfig.class.getDeclaredField("globalConfig"),
                    SongConfig.class.getDeclaredField("ignoreFields")
            });
        } catch (NoSuchFieldException e) {
            ignoreFields = FileSaver.ignoreFields;
        }
    }

    public SongConfig(File saveFile) throws FileSaverException, IOException {
        super(saveFile);
    }

    public static void setGlobalConfig(Config globalConfig) {
        if (globalConfig == null) return;
        SongConfig.globalConfig = globalConfig;
    }

    public int getVolumePercent() {
        if (volumePercent == null) {
            return globalConfig.getDefaultVolumePercent();
        }
        return volumePercent;
    }

    public boolean setVolumePercent(Integer volumePercent) throws IOException {
        return updateField(
                "volumePercent",
                (volPer) -> volPer <= 100 && volPer >= 0,
                volumePercent
        );
    }
}
