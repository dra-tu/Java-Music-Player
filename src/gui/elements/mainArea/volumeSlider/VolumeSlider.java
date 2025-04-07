package gui.elements.mainArea.volumeSlider;

import musicPlayer.MusicPlayer;
import musicPlayer.event.SongLoadedEvent;

import javax.swing.*;

public class VolumeSlider extends JPanel {
    public VolumeSlider(String headline, VolumeSliderType volType, MusicPlayer musicPlayer) {
        super();

        JLabel headlineLabel = createHeadline(headline);
        JLabel label = createLabel();
        JSlider slider = createSlider();

        add(headlineLabel);
        add(label);
        add(slider);

        VolumeSliderController controller = new VolumeSliderController(musicPlayer, slider, label, volType);
        musicPlayer.addEventListener(SongLoadedEvent.class, controller);

    }

    private JLabel createHeadline(String headline) {
        return new JLabel(headline);
    }

    private JLabel createLabel() {
        JLabel label = new JLabel();

        label.setText("??? %");

        return label;
    }

    private JSlider createSlider() {
        JSlider slider = new JSlider();

        slider.setOrientation(JSlider.VERTICAL);
        slider.setMaximum(100);
        slider.setMinimum(0);

        // slider "painting"
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        return slider;
    }
}
