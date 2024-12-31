package tui;

import musicPlayer.MusicPlayer;

import tui.menus.MenuExit;
import tui.menus.MenuManager;
import tui.menus.song.SongMenu;

import tui.terminal.TerminalLock;
import tui.terminal.TerminalPosition;

import static tui.terminal.TerminalColor.RED;
import static tui.terminal.TerminalColor.RESET;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TUI {
    volatile MusicPlayer musicPlayer;
    private final TerminalLock termLock;
    private SongMenu songMenu;

    private String errorLog = "";

    public TUI() {
        musicPlayer = new MusicPlayer();
        termLock = new TerminalLock();
    }

    public void addToErrorLog(String error) {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM HH:mm");
        String date = LocalDateTime.now().format(myFormatObj);
        errorLog += String.format("[ERROR @ %s] %s%n", date, error);
    }

    public String getErrorLog() {
        return errorLog;
    }

    public boolean start(String dirPath, boolean startSong) {
        if (!setDir(dirPath)) return false;
        // Create the Menu Manager
        // The Menu Manager will create the Menus(Home and Song)
        MenuManager menuMgr = new MenuManager(
                new TerminalPosition(1, 4),
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

        songMenu = menuMgr.getSongMenu();
        songMenu.clear();

        // start the Menus and the song Info ui
        songUiUpdater.start();

        if(startSong)
            mixPlay(true);

        menuMgr.start();

        // Menu Manager returning mens that the Home Menu is closed
        // therefor we can now end the program

        songUiUpdater.interrupt(); // exit the Program
        return true;
    }

    public boolean setDir(String dirPath) {
        try {
            System.out.println("Start loading directory");
            if (musicPlayer.useDir(dirPath)) {
                System.out.println("Directory Loaded: " + dirPath);
                return true;
            } else {
                System.out.println(RED + "cant load Directory: " + dirPath + RESET);
                addToErrorLog("cant load Directory: " + dirPath);
                return false;
            }
        } catch (IOException e) {
            System.out.println(RED + "cant load Directory: " + dirPath + RESET);
            addToErrorLog("cant load Directory: " + dirPath);
            return false;
        }
    }

    private boolean loadSong(int songId) {
        if (songId != -1) musicPlayer.historyAddAndJump(songId);
        songId = musicPlayer.getHistorySongId();
        System.out.println("Loading Song ID " + songId + " ...");

        int loadResold;
        try {
            loadResold = musicPlayer.historyLoadSong();
        } catch (IOException e) {
            return false;
        }

        if (loadResold == -1) {
            return false;
        }

        System.out.println("Song loaded");

        return true;
    }

    private boolean playSong() {
        System.out.println("Start playing Song ...");

        return musicPlayer.continueSong();
    }

    public boolean loadAndPlaySong(int songId) {
        boolean songLoaded = loadSong(songId);
        if (!songLoaded) {
            System.out.println(RED + "cannot load Song: " + songId + "  :(" + RESET);
            addToErrorLog("problem by loading song: " + songId);
            return false;
        }

        boolean songPlaying = playSong();
        if (!songPlaying) {
            System.out.println(RED + "cannot play Song: " + songId + " :(" + RESET);
            addToErrorLog("problem by playing song: " + songId);
            return false;
        }

        return true;
    }

    public void mixPlay(boolean newSong) {
        if (newSong)
            musicPlayer.historyNext(1);

        MenuExit status = MenuExit.NORMAL;
        do {
            if (!loadAndPlaySong(-1)) {
                musicPlayer.historyNext(1);
                continue;
            }

            songMenu.clear();
            status = songMenu.start();
            if (status == MenuExit.SONG_BACK) { // go one back in History
                musicPlayer.historyBack(1);
            } else { // go one forward in history
                musicPlayer.historyNext(1);
            }
        } while (status != MenuExit.USER_EXIT);
    }

    public boolean mixPlayFromId(int songId) {
        if (!musicPlayer.historyAdd(songId))
            return false;
        musicPlayer.historyGoNewest();
        mixPlay(false);
        return true;
    }

    public boolean mixPlayFromHistory(int historyPose) {
        if (!musicPlayer.historyGoTo(historyPose))
            return false;
        mixPlay(false);
        return true;
    }
}
