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

    public void setDir(String dirPath) {
        if (musicPlayer.useDir(dirPath)) {
            System.out.println(dirPath + " Loaded!");
        } else {
            System.out.println(dirPath + "can not be Loaded!!!!!!!!!!");
        }
    }

    public void start() {
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
        Thread menuThread = new Thread(menuMgr);

        TUISongDisplay songUiUpdater = new TUISongDisplay(
                menuThread,
                musicPlayer,
                termLock
        );
        Thread updaterThread = new Thread(songUiUpdater);

        // Starting the Threads
        menuThread.start();
        updaterThread.start();

        // exit the Program
        try {
            menuThread.join();
        } catch (InterruptedException _) {}
        updaterThread.interrupt();
    }
}
