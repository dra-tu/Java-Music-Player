package TUI.Menus;

import MusicPlayer.MusicPlayer;
import TUI.Terminal.*;

public abstract class MenuBase {
    // default Strings
    String prompt = "? ";
    String unknownMsg = "unknown Option";
    String exitMsg = "BY";
    String inputMsg = "=> ";
    String optionsMenu = "no Options";

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

    void clear() {
        termLock.lockTerminal();
        TerminalControl.clear();
        TerminalControl.setCursorPos(startPos);
        termLock.unlockTerminal();
    }

    public void start() {
        setUp();
        action();
    }

    void setUp() {
        termLock.lockTerminal();
        TerminalControl.setCursorPos(startPos);
        termLock.unlockTerminal();
    }

    abstract void action();
    abstract void quid();
}
