package tui.menus;

import java.util.HashMap;

public class ReturnValue<E extends Enum<E>> {
    public final String HELP_STRING;
    public final HashMap<String, E> KEY_MAP;

    public ReturnValue(String helpString, HashMap<String, E> keyMap) {
        HELP_STRING = helpString;
        KEY_MAP = keyMap;
    }
}
