package gui.elements.bottomBar.timeBar;

import musicPlayer.MusicPlayer;
import musicPlayer.songTypes.LoadedSong;
import musicPlayer.TimeStamp;

import javax.swing.*;
import java.util.TimerTask;

public class TimeBarController extends TimerTask {
    private final MusicPlayer musicPlayer;
    private final JLabel label;
    private final JProgressBar bar;

    TimeBarController(JLabel label, JProgressBar bar, MusicPlayer player) {
        this.label = label;
        this.bar = bar;
        this.musicPlayer = player;
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(() -> {
            LoadedSong currentSong;
            long songLength;
            long currentTime;

            currentSong = musicPlayer.getCurrentSong();

            if (currentSong != null) {
                songLength = currentSong.getMaxTime();
                currentTime = currentSong.getCurrentTime();
            } else {
                songLength = 0L;
                currentTime = 0L;
            }

            label.setText(TimeStamp.toString(currentTime) + " / " + TimeStamp.toString(songLength));

            bar.setMaximum((int) songLength);
            bar.setValue((int) currentTime);
        });
    }
}
