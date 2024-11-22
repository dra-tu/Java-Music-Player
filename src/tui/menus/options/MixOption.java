package tui.menus.options;

import java.util.HashMap;

public enum MixOption {
    NEXT("n", "Play next in History"),
    PREVIOUS("b", "go Back in History");

    // this part is the same in all Option Enums
    // EXCEPTIONS: Name of constructor, Type in HasMap, in static for loop, return Type of getByKey
    private final String KEY;
    private final String DESCRIPTION;

    private static final HashMap<String, MixOption> KEY_MAP = new HashMap<>();
    private static final String HELP_STRING;

    MixOption(String KEY, String DESCRIPTION) {
        this.KEY = KEY;
        this.DESCRIPTION = DESCRIPTION;
    }

    static {
        StringBuilder strB = new StringBuilder();
        for(MixOption option: values()) {
            KEY_MAP.put(option.KEY, option);
            strB.append(option.KEY).append(": ").append(option.DESCRIPTION).append(String.format("%n"));
        }
        HELP_STRING = strB.toString();
    }

    public static MixOption getByKey(String key) {
        return KEY_MAP.get(key);
    }

    public static String getHelpString() {
        return HELP_STRING;
    }
}
