package TUI.Menus;

import MusicPlayer.MusicPlayer;
import TUI.Terminal.*;

public abstract class MenuBase {
    // default Strings
    String prompt = "? ";
    String unknownMsg = "unknown Option";
    String exitMsg = "BY";
    String inputMsg = "=> ";

    TerminalPosition startPos;
    TerminalHelper terminalHelper;
    MusicPlayer musicPlayer;
    TerminalLock termLock;
    TerminalInput terminalInput;

    public MenuBase(
            TerminalPosition startPos,
            MusicPlayer musicPlayer,
            TerminalLock termLock,
            TerminalHelper terminalHelper,
            TerminalInput terminalInput
    ) {
        this.startPos = startPos;
        this.musicPlayer = musicPlayer;
        this.termLock = termLock;
        this.terminalHelper = terminalHelper;
        this.terminalInput = terminalInput;
    }

    public void clear() {
        termLock.lockTerminal();
        TerminalControl.clear();
        TerminalControl.setCursorPos(startPos);
        termLock.unlockTerminal();
    }

    // 0: normal exit (user)
    // 1: Exception
    public int start() {
        setUp();
        return action();
    }

    private void setUp() {
        termLock.lockTerminal();
        TerminalControl.setCursorPos(startPos);
        termLock.unlockTerminal();
    }

    abstract int action();
    abstract void quid();
}
