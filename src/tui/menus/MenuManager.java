package tui.menus;

import musicPlayer.MusicPlayer;

import tui.TUI;
import tui.menus.home.HomeMenu;
import tui.menus.song.SongMenu;
import tui.terminal.TerminalLock;
import tui.terminal.TerminalHelper;
import tui.terminal.TerminalPosition;
import tui.terminal.TerminalInput;

public class MenuManager {
    private final SongMenu songMenu;
    private final HomeMenu homeMenu;

    public MenuManager(TerminalPosition startPos, MusicPlayer musicPlayer, TerminalLock termLock, TUI tui) {
        TerminalHelper terminalHelper = new TerminalHelper(termLock);
        TerminalInput inFunc = new TerminalInput(termLock);

        songMenu = new SongMenu(tui, startPos, musicPlayer, termLock, terminalHelper, inFunc);
        homeMenu = new HomeMenu(startPos, musicPlayer, termLock, terminalHelper, inFunc, tui, songMenu);
    }

    public SongMenu getSongMenu() {
        return songMenu;
    }

    public void start() {
        homeMenu.start();
    }
}
