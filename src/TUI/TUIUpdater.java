package TUI;

import MusicPlayer.MusicPlayer;
import MusicPlayer.TimeStamp;
import MusicPlayer.LoadedSong;

public class TUIUpdater implements Runnable {
    MusicPlayer musicPlayer;
    TerminalLock termLock;
    final TerminalPosition END_POS = new TerminalPosition(500, 2);

    public TUIUpdater(MusicPlayer musicPlayer, TerminalLock termLock) {
        this.musicPlayer = musicPlayer;
        this.termLock = termLock;
    }

    @Override
    public void run() {
        SongMenuStart();
    }

    private void SongMenuStart() {
        LoadedSong song = musicPlayer.currentSong;

        final TimeStamp SONG_LENGTH = song.getTimeStampLength();
        final String SONG_NAME = song.getName();
        TimeStamp currentTime = new TimeStamp(0L);

        while (currentTime.compareTo(SONG_LENGTH) > 0) {
            currentTime = song.getTimeStampPosition();

            printSongInfo(currentTime, SONG_LENGTH, SONG_NAME);

            try {
                Thread.sleep(1_000);
            } catch (InterruptedException _) {
                break;
            }
        }
    }

    private void printSongInfo(TimeStamp currentTime, TimeStamp songLength, String songName) {
        termLock.lockTerminal();
        TerminalControl.saveCursorPos();

        TerminalControl.setCursorPos(END_POS);
        TerminalControl.clearToStart();
        TerminalControl.setCursorPos(TerminalPosition.START);
        System.out.println("Playing: " + songName);
        System.out.println(currentTime.getFormatted() + " / " + songLength.getFormatted());

        TerminalControl.loadCursorPos();
        termLock.unlockTerminal();
    }
}
