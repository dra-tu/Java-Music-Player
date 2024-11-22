package tui.menus;

import musicPlayer.MusicPlayer;
import tui.terminal.*;

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
    public int start() {
        setUp();
        return action();
    }

    private void setUp() {
        termLock.lockTerminal();
        TerminalControl.setCursorPos(startPos);
        termLock.unlockTerminal();
    }

    void printHistory() {
        Integer[] history = musicPlayer.getHistory();
        int historyPos = musicPlayer.getHistoryPos();

        int length = (MusicPlayer.HISTORY_MAX_SIZE+"").length();
        int b = (history.length + "").length();

        String historyNumber = " %" + length + "d: ";
        String songId = "(%" + b + "d) ";
        String songNameS = "%s";
        String historyItem = historyNumber + songId + songNameS + "%n";

        String songName;
        for(int i = 0; i < history.length; i++) {
            songName = musicPlayer.getSong(history[i]).getName();

            if(i == historyPos) System.out.print(">");
            System.out.printf(historyItem, i, history[i], songName);
        }
    }

    abstract int action();
    abstract void quid();
}
