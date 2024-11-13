package TUI;

import MusicPlayer.MusicPlayer;

import TUI.Menus.MenuManager;
import TUI.Terminal.TerminalControl;
import TUI.Terminal.TerminalLock;
import TUI.Terminal.TerminalPosition;

public class TUI {
    volatile MusicPlayer musicPlayer;
    TerminalLock termLock;

    public TUI(int maxLoadSongs) {
        musicPlayer = new MusicPlayer(maxLoadSongs);
        termLock = new TerminalLock();
    }

    public boolean setDir(String dirPath){
        if (musicPlayer.useDir(dirPath)) {
            System.out.println(dirPath + "Directory Loaded!");
            return true;
        } else {
            System.out.println("cant load Directory: " + dirPath);
            return false;
        }
    }

    public void start(String dirPath) {

        if(!setDir(dirPath)) return;

        // prepare Terminal
        TerminalControl.clear();

        // create obj to be multithreaded
        // Creating Threads to update the ui and listen to inputController
        MenuManager menuMgr = new MenuManager(
                new TerminalPosition(1, 3),
                musicPlayer,
                termLock,
                this
        );

        TUISongDisplay songUiUpdater = new TUISongDisplay(
                Thread.currentThread(),
                musicPlayer,
                termLock
        );
        Thread updaterThread = new Thread(songUiUpdater);

        // Starting the Threads
        updaterThread.start();
        menuMgr.start();

        // exit the Program
        updaterThread.interrupt();
    }
}
