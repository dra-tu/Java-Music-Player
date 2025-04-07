package gui.elements.bottomBar.timeBar;

import musicPlayer.MusicPlayer;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class TimeBar extends JPanel {
    public TimeBar(MusicPlayer musicPlayer) {

        JLabel label = new JLabel("00:00 / XX:XX");
        JProgressBar bar = new JProgressBar(0, 0);

        add(label);
        add(bar);

        startUpdating(musicPlayer, label, bar);
    }

    private void startUpdating(MusicPlayer musicPlayer, JLabel label, JProgressBar bar) {
        TimerTask timeUpdater = new TimeBarController(label, bar, musicPlayer);

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timeUpdater, 0, 1_000);
    }

    private void addStyle() {

    }
}
