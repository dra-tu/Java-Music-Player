package tui.terminal;

public class TerminalPosition {
    public int x;
    public int y;

    final public static TerminalPosition START = new TerminalPosition(1, 1);

    public TerminalPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
