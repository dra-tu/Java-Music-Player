package TUI;

import MusicPlayer.MusicPlayer;

public class TUI {
    volatile MusicPlayer musicPlayer;
    TUIInputDataControl tuiInputDataControl;
    TerminalLock termLock;

    public TUI(int maxLoadSongs) {
        musicPlayer = new MusicPlayer(maxLoadSongs);
        tuiInputDataControl = new TUIInputDataControl();
        termLock = new TerminalLock();
    }

    public void setDir(String dirPath) {
        if (musicPlayer.useDir(dirPath)) {
            System.out.println(dirPath + " Loaded!");
        } else {
            System.out.println(dirPath + "can not be Loaded!!!!!!!!!!");
        }
    }

    public void start() throws InterruptedException {
        System.out.println("Loading Song ID 0...");

        // des hier kann normal nicht gesehen werden
        // ist dafür da fals was schif geht
        if (!musicPlayer.loadSong(0)) {
            System.out.println("cannot load Song  :(");
            return;
        }

        System.out.println("Song loaded");
        System.out.println("Start playing Song ...");

        if (!musicPlayer.playSong(0)) {
            System.out.println("cannot play Song  :(");
            return;
        }

        // prepare Terminal
        TerminalControl.clear();

        // Creating Threads to update the ui and listen to inputController
        TUIUpdater updater = new TUIUpdater(
                musicPlayer,
                termLock
        );
        Thread updaterThread = new Thread(updater);

        TUIMenuMgr inputController = new TUIMenuMgr(
                new TerminalPosition(1, 3),
                musicPlayer,
                termLock
        );
        Thread inputThread = new Thread(inputController);

        // Starting the Threads
        updaterThread.start();
        inputThread.start();

        // exit the Program
        inputThread.join();
        updaterThread.interrupt();

        musicPlayer.exitSong();
    }
}
