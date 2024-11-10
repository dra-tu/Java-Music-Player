package TUI.Menus;

import MusicPlayer.MusicPlayer;
import TUI.*;

public abstract class Menu {
    // default Strings
    String prompt = "? ";
    String unknownMsg = "unknown Option";
    String exitMsg = "BY";
    String inputMsg = "=> ";
    String optionsMenu = "no Options";

    TerminalPosition startPos;

    Share share;
    MusicPlayer musicPlayer;
    TerminalLock termLock;
    InputFunc inputFunc;

    public Menu(
            TerminalPosition startPos,
            MusicPlayer musicPlayer,
            TerminalLock termLock,
            Share share,
            InputFunc inputFunc
    ) {
        this.startPos = startPos;
        this.musicPlayer = musicPlayer;
        this.termLock = termLock;
        this.share = share;
        this.inputFunc = inputFunc;
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
}
