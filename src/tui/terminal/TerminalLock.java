package tui.terminal;

public class TerminalLock {
    private boolean inUse;

    public TerminalLock() {
        inUse = false;
    }

    public synchronized void lockTerminal() {
        while(inUse) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        inUse = true;
    }

    public synchronized void unlockTerminal() {
        inUse = false;
        notifyAll();
    }
}
