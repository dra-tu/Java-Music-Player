package gui.elements.bottomBar.timeBar;

import musicPlayer.MusicPlayer;

import javax.swing.*;
import java.util.Timer;

public class TimeBar extends JPanel {
    public TimeBar(MusicPlayer musicPlayer) {
        JLabel label = new JLabel("00:00 / XX:XX");
        JSlider bar = new JSlider(0, 0);

        add(label);
        add(bar);

        startUpdating(musicPlayer, label, bar);
    }

    private void startUpdating(MusicPlayer musicPlayer, JLabel label, JSlider bar) {
        TimeBarController controller = new TimeBarController(label, bar, musicPlayer);

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(controller, 0, 1_000);
    }

    private void addStyle() {

    }
}
