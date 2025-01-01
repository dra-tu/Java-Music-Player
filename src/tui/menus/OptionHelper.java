package tui.menus;

import tui.terminal.TerminalColor;

import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.HashMap;

public class OptionHelper {
    public static <E extends Enum<E>> ReturnValue<E> genHelpStringAndKeyMap(Class<E> enumT) {

        StringBuilder strB = new StringBuilder(300);
        HashMap<String, E> keyMap = new HashMap<>();

        for (E option : EnumSet.allOf(enumT)) {

            // get the values of the KEY and DESCRIPTION fields
            String key = getFieldValue(enumT, "KEY", option);
            String description = getFieldValue(enumT, "DESCRIPTION", option);

            // skip options you cant select
            if (key.isEmpty())
                continue;
            // add description to options whit empty desc
            if (description.isEmpty())
                description = "[ NO DESCRIPTION ]";


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

    private static <T extends Enum<T>> String getFieldValue(Class<T> enumT, String name, Object obj) {
        try {
            Field field = enumT.getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj).toString();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return "";
        }
    }
}
