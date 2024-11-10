package TUI.Terminal;

public class TerminalHelper {
    final private TerminalLock termLock;

    public TerminalHelper(TerminalLock termLock) {
        this.termLock = termLock;
    }

    public void savePrint(String string) {
        termLock.lockTerminal();
        System.out.print(string);
        termLock.unlockTerminal();
    }
    public void savePrintln(String string) {
        termLock.lockTerminal();
        System.out.println(string);
        termLock.unlockTerminal();
    }
}
