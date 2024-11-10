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

    public Menu(Share share, MusicPlayer musicPlayer, TerminalLock termLock, TerminalPosition startPos) {
        this.startPos = startPos;
        this.share = share;
        this.musicPlayer = musicPlayer;
        this.termLock = termLock;
        this.inputFunc = new InputFunc(termLock);
    }

    void clear() {
        termLock.lockTerminal();
        TerminalControl.clear();
        TerminalControl.setCursorPos(startPos);
        termLock.unlockTerminal();
    }

    abstract boolean action(String input);
}
