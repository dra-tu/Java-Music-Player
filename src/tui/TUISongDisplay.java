package tui;

import musicPlayer.MusicPlayer;
import musicPlayer.types.LoadedSong;

import tui.terminal.TerminalControl;
import tui.terminal.TerminalLock;
import tui.terminal.TerminalPosition;

public class TUISongDisplay extends Thread {
    private final Thread menuThread;
    private final MusicPlayer musicPlayer;
    private final TerminalLock termLock;
    private final TerminalPosition END_POS = new TerminalPosition(500, 2);

    TUISongDisplay(Thread menuThread, MusicPlayer musicPlayer, TerminalLock termLock) {
        this.menuThread = menuThread;
        this.musicPlayer = musicPlayer;
        this.termLock = termLock;
    }

    @Override
    public void run() {
        // define vars for use in while loop
        LoadedSong currentSong;
        String songName;
        long songLength;
        long currentTime;
        int songId;

        boolean runing = true;
        do {
            currentSong = musicPlayer.getCurrentSong();
            if (currentSong == null) {
                printSongInfo("---", "---", "----", -1);
            } else {
                songName = currentSong.getName();
                songLength = currentSong.getMaxTime();
                currentTime = currentSong.getCurrentTime();
                songId = currentSong.getSongId();

                printSongInfo(
                        TimeStamp.toString(currentTime),
                        TimeStamp.toString(songLength),
                        songName,
                        songId
                );

                // Song Menu will clos and go back to Home Menu when song is done playing
                if(songLength == currentTime) {
                    menuThread.interrupt();
                }
            }

            try {
                Thread.sleep(1_000);
            } catch (InterruptedException _) {
                runing = false; // the program while now be closed
            }
        } while(runing);
    }

    private void printSongInfo(String currentTime, String songLength, String songName, int songId) {
        // save cursor position
        termLock.lockTerminal();
        TerminalControl.saveCursorPos();

        // clear old and set cursor to start
        TerminalControl.setCursorPos(END_POS);
        TerminalControl.clearToStart();
        TerminalControl.setCursorPos(TerminalPosition.START);

        // print the info
        System.out.println("Playing: (" + songId + ") " + songName);
        System.out.println(currentTime + " / " + songLength);

        // reset cursor to before this function
        TerminalControl.loadCursorPos();
        termLock.unlockTerminal();
    }
}
