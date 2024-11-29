package tui.menus.options;

import tui.terminal.TerminalColor;

import java.util.EnumSet;
import java.util.HashMap;

public class OptionHelper {

    public static <E extends Enum<E>> ReturnValue<E> genHelpStringAndKeyMap(Class<E> enumT) {

        StringBuilder strB = new StringBuilder(300);
        HashMap<String, E> keyMap = new HashMap<>();

        for (E option : EnumSet.allOf(enumT)) {

            // get the values of the KEY and DESCRIPTIONfields
            String key;
            String description;
            try {
                key = enumT.getDeclaredField("KEY").get(option).toString();
                description = enumT.getDeclaredField("DESCRIPTION").get(option).toString();
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Fuck");
            }

            // add key, option pare to the map
            keyMap.put(key, option);

            // add key, description pare to the help string
            strB
                    .append(TerminalColor.GREEN)
                    .append(String.format("%2s", key))
                    .append(TerminalColor.RESET)
                    .append(": ")
                    .append(TerminalColor.BLUE)
                    .append(description)
                    .append(TerminalColor.RESET)
                    .append(String.format("%n"));

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
