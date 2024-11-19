package tui;

import musicPlayer.MusicPlayer;

import tui.menus.MenuManager;
import tui.menus.SongMenu;

import tui.terminal.TerminalColor;
import tui.terminal.TerminalControl;
import tui.terminal.TerminalLock;
import tui.terminal.TerminalPosition;

import java.util.Random;

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

    private boolean loadSong(int songId, boolean addToHistory) {
        System.out.println("Loading Song ID " + songId + " ...");

        // des hier kann normal nicht gesehen werden
        // ist dafür da fals was schif geht
        int loadResold = musicPlayer.loadSong(songId, addToHistory);
        if (loadResold == -1) {
            System.out.println(TerminalColor.RED + "cannot load Song  :(" + TerminalColor.RESET);
            return false;
        }

        System.out.println("Song loaded");

        return true;
    }

    public boolean loadAndPlaySong(int songId, boolean addToHistory) {
        if(!loadSong(songId, addToHistory)) return false;
        return playSong();
    }

    public void mixPlay() {
        final int SONG_COUNT = musicPlayer.getSongs().length;
        SongMenu songMenu = menuMgr.getSongMenu();
        songMenu.setMixPlay(true);

        Random rng = new Random();

        int historyPos = -1;
        int status;
        int maxIndex;
        Integer[] history;
        do {
            if(historyPos == -1) {
                loadSong(rng.nextInt(0, SONG_COUNT), true);
                historyPos = 0;
            }

            history = musicPlayer.getHistory();

            loadAndPlaySong(history[historyPos], false);

            maxIndex = musicPlayer.getHistory().length - 1;

            status = songMenu.start();
            if (status == 1) { // go one back in History
                historyPos = Math.min(historyPos + 1, maxIndex);
            } else { // go one forward in history
                historyPos = Math.max(historyPos - 1, -1);
            }
        } while(status != 0);

        songMenu.setMixPlay(false);
    }
}
