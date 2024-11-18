package TUI;

import MusicPlayer.MusicPlayer;
import MusicPlayer.Types.LoadedSong;

import TUI.Terminal.TerminalControl;
import TUI.Terminal.TerminalLock;
import TUI.Terminal.TerminalPosition;

public class TUISongDisplay extends Thread {
    Thread menuThread;
    MusicPlayer musicPlayer;
    TerminalLock termLock;
    final TerminalPosition END_POS = new TerminalPosition(500, 2);

    public TUISongDisplay(Thread menuThread, MusicPlayer musicPlayer, TerminalLock termLock) {
        this.menuThread = menuThread;
        this.musicPlayer = musicPlayer;
        this.termLock = termLock;
    }

    @Override
    public void run() {
        SongMenuStart();
    }

    private void SongMenuStart() {
        long songLength;
        String songName;
        long currentTime;

        while (true) {
            LoadedSong song = musicPlayer.getCurrentSong();
            if (song == null) {
                printSongInfo("---", "---", "----");
            } else {
                currentTime = song.getCurrentTime();
                songLength = song.getMaxTime();
                songName = song.getName();

                printSongInfo(
                        TimeStamp.format(currentTime),
                        TimeStamp.format(songLength),
                        songName
                );

                if(songLength == currentTime) {
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
