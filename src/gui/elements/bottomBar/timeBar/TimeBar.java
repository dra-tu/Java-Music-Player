package gui.elements.bottomBar.timeBar;

import gui.color.ColorMgr;
import gui.elements.jmp.JmpSlider;
import musicPlayer.MusicPlayer;

import javax.swing.*;
import java.util.Timer;

public class TimeBar extends JPanel {
    private JLabel label;
    private JmpSlider slider;

    public TimeBar(ColorMgr colorMgr, MusicPlayer musicPlayer) {
        label = new JLabel("00:00 / XX:XX");
        slider = new JmpSlider(colorMgr, 0, 0);

        add(label);
        add(slider);

        startUpdating(musicPlayer, label, slider);
    }

    private void startUpdating(MusicPlayer musicPlayer, JLabel label, JSlider bar) {
        TimeBarController controller = new TimeBarController(label, bar, musicPlayer);

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(controller, 0, 1_000);
    }
}
