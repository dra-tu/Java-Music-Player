package TUI;

public class Share {
    final private TerminalLock termLock;

    public Share(TerminalLock termLock) {
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
