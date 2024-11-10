package TUI;

public class TerminalPosition {
    public int x;
    public int y;

    final static TerminalPosition START = new TerminalPosition(1,1);

    public TerminalPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
