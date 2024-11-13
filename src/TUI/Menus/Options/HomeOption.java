package TUI.Menus.Options;

import java.util.HashMap;

public enum HomeOption {
    LIST("i", "lIst"),
    PLAY("p", "Play"),
    MIX("m", "Mix"),
    RELOAD("r", "Reload"),
    CLEAR("l", "cLear"),
    HELP("?", "help"),
    QUIT("q", "Quit");

    // this part is the same in all Option Enums
    // EXCEPTIONS: Name of constructor, Type in HasMap, in static for loop, return Type of getByKey
    private final String key;
    private final String description;

    private static final HashMap<String, HomeOption> KEY_MAP = new HashMap<>();
    private static String HELP_STRING = "";

    HomeOption(String key, String description) {
        this.key = key;
        this.description = description;
    }

    static {
        StringBuilder strB = new StringBuilder(HELP_STRING);
        for(HomeOption option: values()) {
            KEY_MAP.put(option.key, option);
            strB.append(option.key).append(": ").append(option.description).append(String.format("%n"));
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