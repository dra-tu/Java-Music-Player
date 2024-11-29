package tui.menus.options;

import java.util.HashMap;

public enum HomeOption {
    PLAY("p", "Play"),
    PLAY_FROM_SONG_ID("ps", "Start play whit specific song"),
    PLAY_FROM_HISTORY("ph", "Start Play from point in history"),
    LIST("l", "List"),
    LIST_HISTORY("lh", "list History"),
    LIST_ERRORS("le", "list errors"),
    RELOAD("r", "Reload"),
    CLEAR("c", "Clear"),
    HELP("?", "Help"),
    QUIT("q", "Quit");

    // this part is the same in all Option Enums
    // EXCEPTIONS: Name of constructor, Type in HasMap, in static for loop, return Type of getByKey
    final String KEY;
    final String DESCRIPTION;

    private static final HashMap<String, HomeOption> KEY_MAP;
    private static final String HELP_STRING;

    HomeOption(String KEY, String DESCRIPTION) {
        this.KEY = KEY;
        this.DESCRIPTION = DESCRIPTION;
    }

    public static HomeOption getByKey(String key) {
        return KEY_MAP.get(key);
    }

    public static String getHelpString() {
        return HELP_STRING;
    }

    static {
        OptionHelper.ReturnValue<HomeOption> b = OptionHelper.genHelpStringAndKeyMap(HomeOption.class);
        HELP_STRING = b.HELP_STRING;
        KEY_MAP = b.KEY_MAP;
    }
}