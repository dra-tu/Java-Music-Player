package TUI.Terminal;

// NOTE: This Class does not implement TerminalLock functions
//       Because some code needs to run multiple of these methods in one Lock

public class TerminalControl {
    public static void clear() {
        setCursorPos(TerminalPosition.START);
        printCommand("J");
    }

    public static void clearToStart() {
        printCommand("1J");
    }

    public static void setCursorPos(TerminalPosition pos) {
        printCommand(pos.y + ";" + pos.x + "H");
    }

    public static void moveCursorLeft(int n) {
        printCommand(n + "D");
    }

    public static void clearLine() {
        printCommand("K");
    }

    public static void saveCursorPos() {
        printCommand("s");
    }

    public static void loadCursorPos() {
        printCommand("u");
    }

    private static void printCommand(String string) {
        System.out.print("\033[" + string);
    }
}
