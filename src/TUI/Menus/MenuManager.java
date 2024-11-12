package TUI.Menus;

import MusicPlayer.MusicPlayer;
import TUI.TUI;
import TUI.Terminal.TerminalInput;
import TUI.Terminal.TerminalHelper;
import TUI.Terminal.TerminalLock;
import TUI.Terminal.TerminalPosition;

public class MenuManager implements Runnable {
    MusicPlayer musicPlayer;

    SongMenu songMenu;
    HomeMenu homeMenu;

    public MenuManager(TerminalPosition startPos, MusicPlayer musicPlayer, TerminalLock termLock, TUI tui) {
        this.musicPlayer = musicPlayer;

        TerminalHelper terminalHelper = new TerminalHelper(termLock);
        TerminalInput inFunc = new TerminalInput(termLock);

        songMenu = new SongMenu(startPos, musicPlayer, termLock, terminalHelper, inFunc);
        homeMenu = new HomeMenu(startPos, musicPlayer, termLock, terminalHelper, inFunc, this, songMenu);
    }

    // TODO: wired position in code(think about it and maybe move)
    public boolean startSong(int SongId) {
        System.out.println("Loading Song ID " + SongId + " ...");

        // des hier kann normal nicht gesehen werden
        // ist dafür da fals was schif geht
        int loadResold = musicPlayer.loadSong(SongId);
        if (loadResold == -1) {
            System.out.println("cannot load Song  :(");
            return false;
        }

        System.out.println("Song loaded");
        System.out.println("Start playing Song ...");

        if (!musicPlayer.playSong(SongId)) {
            System.out.println("cannot play Song  :(");
            return false;
        }

        return true;
    }

    // TODO: implement method
    // TODO: wired position in code(think about it and maybe move)
    public boolean startMixPlay() {

        return true;
    }

    @Override
    public void run() {
        homeMenu.start();
    }
}
