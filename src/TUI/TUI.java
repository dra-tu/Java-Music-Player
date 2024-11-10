package TUI;

import MusicPlayer.MusicPlayer;
import TUI.Menus.MenuManager;

public class TUI {
    volatile MusicPlayer musicPlayer;
    TerminalLock termLock;

    TUIUpdater songUiUpdater;
    MenuManager menuMgr;

    public TUI(int maxLoadSongs) {
        musicPlayer = new MusicPlayer(maxLoadSongs);
        termLock = new TerminalLock();

        songUiUpdater = new TUIUpdater(
                musicPlayer,
                termLock
        );

        menuMgr = new MenuManager(
                new TerminalPosition(1, 3),
                musicPlayer,
                termLock,
                this
        );
    }

    public void setDir(String dirPath) {
        if (musicPlayer.useDir(dirPath)) {
            System.out.println(dirPath + " Loaded!");
        } else {
            System.out.println(dirPath + "can not be Loaded!!!!!!!!!!");
        }
    }

    public void start() throws InterruptedException {
        // prepare Terminal
        TerminalControl.clear();

        // Creating Threads to update the ui and listen to inputController
        Thread menuThread = new Thread(menuMgr);
        Thread updaterThread = new Thread(songUiUpdater);

        // Starting the Threads
        menuThread.start();
        updaterThread.start();

        // exit the Program
        menuThread.join();
        updaterThread.interrupt();
    }
}
