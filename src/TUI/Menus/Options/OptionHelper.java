package TUI.Menus.Options;

import java.util.EnumSet;
import java.util.HashMap;

public class OptionHelper {

    public static <E extends Enum<E>> ReturnValue<E> genHelpStringAndKeyMap(Class<E> enumT) {

        StringBuilder strB = new StringBuilder(50);
        HashMap<String, E> keyMap = new HashMap<>();

        for(E option: EnumSet.allOf(enumT) ) {

            String key;
            String description;
            try {
                key = enumT.getDeclaredField("KEY").get(option).toString();
                description = enumT.getDeclaredField("DESCRIPTION").get(option).toString();
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            keyMap.put(key, option);
            strB.append(key).append(": ").append(description).append(String.format("%n"));

        }

        return new ReturnValue<>(strB.toString(), keyMap);
    }

    public static class ReturnValue<E extends Enum<E>> {
        final String HELP_STRING;
        final HashMap<String, E> KEY_MAP;

        public ReturnValue(String helpString, HashMap<String, E> keyMap) {
            HELP_STRING = helpString;
            KEY_MAP = keyMap;
        }
    }
}
