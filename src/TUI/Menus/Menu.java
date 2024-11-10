package TUI.Menus;

import MusicPlayer.MusicPlayer;
import TUI.*;

import java.util.HashMap;

public abstract class Menu {
    // default Strings
    String prompt = "? ";
    String unknownMsg = "WHAT?";
    String exitMsg = "BY";
    String inputMsg = ">>";
    String optionsMenu = "no Options";
    HashMap<String, SongOption> songOptions = new HashMap<>();

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

    public void start() {
        setUp();
        action();
    }

    void clear() {
        termLock.lockTerminal();
        TerminalControl.clear();
        TerminalControl.setCursorPos(startPos);
        termLock.unlockTerminal();
    }

    abstract void setUp();
    abstract void action();
}
