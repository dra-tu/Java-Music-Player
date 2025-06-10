package gui.elements.mainArea.volumeSlider;

import gui.color.ColorMgr;
import gui.elements.jmp.JmpSlider;
import musicPlayer.MusicPlayer;
import musicPlayer.event.SongLoadedEvent;

import javax.swing.*;

public class VolumeSlider extends JPanel {
    public VolumeSlider(String headline, VolumeSliderType volType,ColorMgr colorMgr, MusicPlayer musicPlayer) {
        super();

        JLabel headlineLabel = createHeadline(headline);
        JLabel label = createLabel();
        JmpSlider slider = createSlider(colorMgr);

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

    private JmpSlider createSlider(ColorMgr colorMgr) {
        JmpSlider slider = new JmpSlider(colorMgr, 0, 100);

        slider.setOrientation(JSlider.VERTICAL);

        // slider "painting"
        slider.setMajorTickSpacing(20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        return slider;
    }
}
