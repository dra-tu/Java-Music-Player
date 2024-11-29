package tui.menus.options;

import java.util.HashMap;

import tui.menus.options.OptionHelper.ReturnValue;

public enum SongOption {
    PAUSE("p", "Pause"),
    CONTINUE("o", "Continue"),
    NEXT("n", "Play next in History"),
    BACK("b", "go Back in History"),
    JUMP("j", "Jump"),
    SKIP("s", "Skips"),
    REWIND("r", "Rewind"),
    LIST_SONGS("ls", "List songs"),
    LIST_HISTORY("lh", "list history"),
    LIST_ERRORS("le", "list errors"),
    CLEAR("c", "Clear"),
    QUIT("q", "Quit"),
    HELP("?", "Help");

    // this part is the same in all Option Enums
    // EXCEPTIONS: Name of constructor, Type in HasMap, in static for loop, return Type of getByKey
    final String KEY;
    final String DESCRIPTION;

    private static final HashMap<String, SongOption> KEY_MAP;
    private static final String HELP_STRING;

    SongOption(String KEY, String DESCRIPTION) {
        this.KEY = KEY;
        this.DESCRIPTION = DESCRIPTION;
    }

    public static SongOption getByKey(String key) {
        return KEY_MAP.get(key);
    }

    public static String getHelpString() {
        return HELP_STRING;
    }

    static {
        ReturnValue<SongOption> b = OptionHelper.genHelpStringAndKeyMap(SongOption.class);
        HELP_STRING = b.HELP_STRING;
        KEY_MAP = b.KEY_MAP;
    }
}