package TUI.Menus;

import MusicPlayer.MusicPlayer;

import TUI.TUI;
import TUI.Terminal.TerminalLock;
import TUI.Terminal.TerminalHelper;
import TUI.Terminal.TerminalPosition;
import TUI.Terminal.TerminalInput;

public class MenuManager {
    MusicPlayer musicPlayer;

    SongMenu songMenu;
    HomeMenu homeMenu;

    public MenuManager(TerminalPosition startPos, MusicPlayer musicPlayer, TerminalLock termLock, TUI tui) {
        this.musicPlayer = musicPlayer;

        TerminalHelper terminalHelper = new TerminalHelper(termLock);
        TerminalInput inFunc = new TerminalInput(termLock);

        songMenu = new SongMenu(startPos, musicPlayer, termLock, terminalHelper, inFunc);
        homeMenu = new HomeMenu(startPos, musicPlayer, termLock, terminalHelper, inFunc, tui, songMenu);
    }

    public SongMenu getSongMenu() {
        return songMenu;
    }

    public void start() {
        homeMenu.start();
    }
}
