package TUI.Menus;

import MusicPlayer.MusicPlayer;
import TUI.TUI;
import TUI.InputFunc;
import TUI.Share;
import TUI.TerminalLock;
import TUI.TerminalPosition;


public class MenuManager implements Runnable {
    MusicPlayer musicPlayer;

    SongMenu songMenu;
    HomeMenu homeMenu;

    public MenuManager(TerminalPosition startPos, MusicPlayer musicPlayer, TerminalLock termLock, TUI tui) {
        this.musicPlayer = musicPlayer;

        Share share = new Share(termLock);
        InputFunc inFunc = new InputFunc(termLock);

        songMenu = new SongMenu(startPos, musicPlayer, termLock, share, inFunc);
        homeMenu = new HomeMenu(startPos, musicPlayer, termLock, share, inFunc, this, songMenu);
    }

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

    @Override
    public void run() {
        homeMenu.start();
    }
}
