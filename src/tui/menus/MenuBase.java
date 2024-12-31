package tui.menus;

import musicPlayer.MusicPlayer;
import musicPlayer.songTypes.Song;
import tui.TUI;
import tui.terminal.*;

import static tui.terminal.TerminalColor.*;

public abstract class MenuBase {
    protected static String prompt;
    protected static String exitMsg;
    protected static String unknownMsg = RED + "unknown Option" + RESET;
    protected static String inputMsg = YELLOW + "=> " + RESET;

    protected String songList;

    protected TUI tui;
    protected TerminalPosition startPos;
    protected TerminalHelper terminalHelper;
    protected MusicPlayer musicPlayer;
    protected TerminalLock termLock;
    protected TerminalInput terminalInput;


    public MenuBase(
            TUI tui,
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
        this.tui = tui;
        genSongList();
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

    public void genSongList() {
        StringBuilder out = new StringBuilder();
        Song[] songs = musicPlayer.getSongs();
        final int indexSize = Integer.toString(songs.length).length();
        final String formatMask = "%" + indexSize + "d";

        for (Song song : songs) {
            out.append("(")
                    .append(BLUE)
                    .append(String.format(formatMask, song.SONG_ID))
                    .append(RESET)
                    .append(") ")
                    .append(song.getName())
                    .append(String.format("%n"));
        }

        songList = out.toString();
    }

    public void printHistory() {
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

    protected abstract MenuExit action();

    protected abstract void quid();

    // GETTER

    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }

    public String getSongList() {
        return songList;
    }

    public TerminalPosition getStartPos() {
        return startPos;
    }

    public TerminalHelper getTerminalHelper() {
        return terminalHelper;
    }

    public TerminalInput getTerminalInput() {
        return terminalInput;
    }

    public TerminalLock getTermLock() {
        return termLock;
    }

    public TUI getTui() {
        return tui;
    }

    public static String getUnknownMsg() {
        return unknownMsg;
    }
}
