package tui.menus;

import musicPlayer.MusicPlayer;
import tui.terminal.*;

import static tui.terminal.TerminalColor.*;

public abstract class MenuBase {
    static String prompt;
    static String exitMsg;
    static String unknownMsg = RED + "unknown Option" + RESET;
    static String inputMsg = YELLOW + "=> " + RESET;

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

    public MenuExit start() {
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

        int charLength = (MusicPlayer.HISTORY_MAX_SIZE + "").length();
        int b = (history.length + "").length();

        String pointer = YELLOW + ">" + RESET;
        String historyNumber = GREEN + " %" + charLength + "d" + RESET + ": ";
        String songId = "(" + BLUE + "%" + b + "d" + RESET + ") ";
        String songNameS = "%s";

        String historyItem = historyNumber + songId + songNameS + "%n";

        String songName;
        for (int i = 0; i < history.length; i++) {
            songName = musicPlayer.getSong(history[i]).getName();

            if (i == historyPos) System.out.print(pointer);
            System.out.printf(historyItem, i, history[i], songName);
        }
    }

    abstract MenuExit action();

    abstract void quid();
}
