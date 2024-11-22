package tui;

import musicPlayer.MusicPlayer;

import tui.menus.MenuManager;
import tui.menus.SongMenu;

import tui.terminal.TerminalColor;
import tui.terminal.TerminalControl;
import tui.terminal.TerminalLock;
import tui.terminal.TerminalPosition;

public class TUI {
    volatile MusicPlayer musicPlayer;
    TerminalLock termLock;
    MenuManager menuMgr;

    public TUI() {
        musicPlayer = new MusicPlayer();
        termLock = new TerminalLock();
    }

    public void start(String dirPath) {

        if (!setDir(dirPath)) return;

        // prepare Terminal
        TerminalControl.clear();

        // Create the Menu Manager
        // The Menu Manager will create the Menus(Home and Song)
        menuMgr = new MenuManager(
                new TerminalPosition(1, 3),
                musicPlayer,
                termLock,
                this
        );

        // create the song info UI
        TUISongDisplay songUiUpdater = new TUISongDisplay(
                Thread.currentThread(),
                musicPlayer,
                termLock
        );

        // start the Menus and the song Info ui
        songUiUpdater.start();
        menuMgr.start();

        // Menu Manager returning mens that the Home Menu is closed
        // therefor we can now end the program

        songUiUpdater.interrupt(); // exit the Program
    }

    private boolean setDir(String dirPath) {
        if (musicPlayer.useDir(dirPath)) {
            System.out.println(dirPath + "Directory Loaded!");
            return true;
        } else {
            System.out.println("cant load Directory: " + dirPath);
            return false;
        }
    }

    private boolean playSong() {
        System.out.println("Start playing Song ...");

        if (!musicPlayer.continueSong()) {
            System.out.println("cannot play Song  :(");
            return false;
        }

        return true;
    }

    private boolean loadSong(int songId) {
        System.out.println("Loading Song ID " + songId + " ...");

        // des hier kann normal nicht gesehen werden
        // ist dafür da fals was schif geht
        if(songId != -1) musicPlayer.historyAddAndJump(songId);

        int loadResold = musicPlayer.historyLoadSong();

        if (loadResold == -1) {
            System.out.println(TerminalColor.RED + "cannot load Song  :(" + TerminalColor.RESET);
            return false;
        }

        System.out.println("Song loaded");

        return true;
    }

    public boolean loadAndPlaySong(int songId) {
        if(!loadSong(songId)) return false;
        return playSong();
    }

    public void mixPlay() {
        SongMenu songMenu = menuMgr.getSongMenu();
        songMenu.setMixPlay(true);

        musicPlayer.historyNext(1);
        int status;
        do {
            loadAndPlaySong(-1);

            songMenu.clear();
            status = songMenu.start();
            if (status == 1) { // go one back in History
                musicPlayer.historyBack(1);
            } else { // go one forward in history
                musicPlayer.historyNext(1);
            }
        } while(status != 0);

        songMenu.setMixPlay(false);
    }
}
