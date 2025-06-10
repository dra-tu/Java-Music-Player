package gui.elements.bottomBar.timeBar;

import musicPlayer.MusicPlayer;
import musicPlayer.songTypes.LoadedSong;
import musicPlayer.TimeStamp;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.TimerTask;

public class TimeBarController extends TimerTask implements ChangeListener {
    private final MusicPlayer musicPlayer;
    private final JLabel label;
    private final JSlider bar;
    private boolean selfChange;

    TimeBarController(JLabel label, JSlider bar, MusicPlayer player) {
        this.label = label;
        this.bar = bar;
        this.musicPlayer = player;
        selfChange = true;

        this.bar.addChangeListener(this);
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

            if (bar.getMaximum() != songLength) {
                selfChange = true;
                bar.setMaximum((int) songLength);
            }
            selfChange = true;
            bar.setValue((int) currentTime);
        });
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (selfChange) {
            selfChange = false;
            return;
        }

        throw new RuntimeException("It is working");
    }
}
