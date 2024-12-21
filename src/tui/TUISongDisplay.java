package tui;

import musicPlayer.MusicPlayer;
import musicPlayer.songTypes.LoadedSong;

import tui.terminal.TerminalColor;
import tui.terminal.TerminalControl;
import tui.terminal.TerminalLock;
import tui.terminal.TerminalPosition;

public class TUISongDisplay extends Thread {
    private final Thread menuThread;
    private final MusicPlayer musicPlayer;
    private final TerminalLock termLock;
    private final TerminalPosition END_POS = new TerminalPosition(500, 3);

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

        int volumePercent;

        boolean runing = true;
        do {
            currentSong = musicPlayer.getCurrentSong();
            if (currentSong == null) {
                printSongInfo("---", "---", "----", -1, 0);
            } else {
                songName = currentSong.getName();
                songLength = currentSong.getMaxTime();
                currentTime = currentSong.getCurrentTime();
                songId = currentSong.getSongId();

                volumePercent = currentSong.getVolumePercent();

                printSongInfo(
                        TimeStamp.toString(currentTime),
                        TimeStamp.toString(songLength),
                        songName,
                        songId,
                        volumePercent
                );

                // Song Menu will clos and go back to Home Menu when song is done playing
                if (songLength == currentTime) {
                    menuThread.interrupt();
                }
            }

            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                runing = false; // the program while now be closed
            }
        } while (runing);
    }

    private void printSongInfo(String currentTime, String songLength, String songName, int songId, int volumePercent) {
        // save cursor position
        termLock.lockTerminal();
        TerminalControl.saveCursorPos();

        // clear old and set cursor to start
        TerminalControl.setCursorPos(END_POS);
        TerminalControl.clearToStart();
        TerminalControl.setCursorPos(TerminalPosition.START);

        // make String for Volume bar
        int length = Integer.toString(volumePercent).length();
        String coloredPercent = String.format(TerminalColor.Cyan + "%d%%" +TerminalColor.RESET, volumePercent);
        String volBar = new StringBuilder(33)
                .repeat("=", volumePercent / 5)
                .repeat(" ", 20 - volumePercent / 5)
                .replace(11 - length, 12, coloredPercent)
                .insert(0, "[")
                .append("]")
                .toString();

        // print the info
        System.out.println("Playing: (" + songId + ") " + songName);
        System.out.println("Volume:  " + volBar);
        System.out.println("Time:    " + currentTime + " / " + songLength);

        // reset cursor to before this function
        TerminalControl.loadCursorPos();
        termLock.unlockTerminal();
    }
}
