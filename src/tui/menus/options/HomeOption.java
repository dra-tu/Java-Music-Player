package tui.menus.options;

import java.util.HashMap;

public enum HomeOption {
    LIST("l", "List"),
    PLAY("p", "Play"),
    MIX("m", "Mix"),
    RELOAD("r", "Reload"),
    CLEAR("c", "Clear"),
    HELP("?", "Help"),
    QUIT("q", "Quit");

    // this part is the same in all Option Enums
    // EXCEPTIONS: Name of constructor, Type in HasMap, in static for loop, return Type of getByKey
    private final String KEY;
    private final String DESCRIPTION;

    private static final HashMap<String, HomeOption> KEY_MAP = new HashMap<>();
    private static final String HELP_STRING;

    HomeOption(String KEY, String DESCRIPTION) {
        this.KEY = KEY;
        this.DESCRIPTION = DESCRIPTION;
    }

    static {
        StringBuilder strB = new StringBuilder();
        for(HomeOption option: values()) {
            KEY_MAP.put(option.KEY, option);
            strB.append(option.KEY).append(": ").append(option.DESCRIPTION).append(String.format("%n"));
        }
        HELP_STRING = strB.toString();
    }

    public static HomeOption getByKey(String key) {
        return KEY_MAP.get(key);
    }

    public static String getHelpString() {
        return HELP_STRING;
    }
}