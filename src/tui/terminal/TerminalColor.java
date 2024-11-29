package tui.terminal;

public enum TerminalColor {
    // Reset
    RESET("\033[0m"),

    // colors
    RED("\033[0;31m"),
    GREEN("\033[0;92m"),
    BLUE("\033[0;34m");

    final String ANSI_ESCAPE_CODE;

    TerminalColor(String ansiEscapeCode) {
        ANSI_ESCAPE_CODE = ansiEscapeCode;
    }

    @Override
    public String toString() {
        return ANSI_ESCAPE_CODE;
    }
}
