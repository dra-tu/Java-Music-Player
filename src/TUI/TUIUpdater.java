package TUI;

import MusicPlayer.MusicPlayer;
import MusicPlayer.TimeStamp;
import MusicPlayer.LoadedSong;

public class TUIUpdater implements Runnable {
    Thread menuThread;
    MusicPlayer musicPlayer;
    TerminalLock termLock;
    final TerminalPosition END_POS = new TerminalPosition(500, 2);

    private boolean infoSent;

    public TUIUpdater(Thread menuThread, MusicPlayer musicPlayer, TerminalLock termLock) {
        this.menuThread = menuThread;
        this.musicPlayer = musicPlayer;
        this.termLock = termLock;

        infoSent = true;
    }

    @Override
    public void run() {
        SongMenuStart();
    }

    private void SongMenuStart() {
        TimeStamp songLength;
        String songName;
        TimeStamp currentTime;

        while (true) {
            LoadedSong song = musicPlayer.getCurrentSong();
            if (song == null) {
                printSongInfo("---", "---", "----");
            } else {
                infoSent = false;

                songLength = song.getTimeStampLength();
                songName = song.getName();
                currentTime = song.getTimeStampPosition();

                printSongInfo(currentTime.getFormatted(), songLength.getFormatted(), songName);

                // System.out.println(!infoSent + " : " + (songLength.compareTo(currentTime) == 0));
                if((songLength.compareTo(currentTime) == 0) && !infoSent) {
                    menuThread.interrupt();
                }
            }

            try {
                Thread.sleep(1_000);
            } catch (InterruptedException _) {
                break;
            }
        }
    }

    private void printSongInfo(String currentTime, String songLength, String songName) {
        termLock.lockTerminal();
        TerminalControl.saveCursorPos();

        TerminalControl.setCursorPos(END_POS);
        TerminalControl.clearToStart();
        TerminalControl.setCursorPos(TerminalPosition.START);

        System.out.println("Playing: " + songName);
        System.out.println(currentTime + " / " + songLength);

        TerminalControl.loadCursorPos();
        termLock.unlockTerminal();
    }
}
