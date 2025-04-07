package musicPlayer.timerTask;

import musicPlayer.MusicPlayer;
import musicPlayer.event.SongIsDoneEvent;
import musicPlayer.songTypes.LoadedSong;

import java.util.TimerTask;

public class TimerTaskSongCheck extends TimerTask {
    volatile MusicPlayer musicPlayer;
    volatile SongIsDoneEvent songDoneEvent;

    private long lastEventCall;
    private final long TIME_BETWEEN_CALLS_MILLISECONDS = 750L;

    public TimerTaskSongCheck(MusicPlayer musicPlayer, SongIsDoneEvent songDoneEvent) {
        this.musicPlayer = musicPlayer;
        this.songDoneEvent = songDoneEvent;

        lastEventCall = 0L;
    }

    @Override
    public void run() {
        LoadedSong currentSong = musicPlayer.getCurrentSong();
        if (currentSong == null) return;

        long songLength = currentSong.getMaxTime();
        long songCurrentTime = currentSong.getCurrentTime();

        if (songLength <= songCurrentTime) {
            long currentTime = System.currentTimeMillis();

            if (Math.abs(lastEventCall - currentTime) >= TIME_BETWEEN_CALLS_MILLISECONDS) {
                songDoneEvent.callListeners();
                lastEventCall = System.currentTimeMillis();
            }
        }
    }
}
