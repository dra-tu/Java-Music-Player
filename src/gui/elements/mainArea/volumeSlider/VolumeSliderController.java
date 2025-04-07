package gui.elements.mainArea.volumeSlider;

import musicPlayer.MusicPlayer;
import musicPlayer.event.jmpEvent.JmpEvent;
import musicPlayer.event.jmpEvent.JmpEventListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.IOException;

public class VolumeSliderController implements JmpEventListener, ChangeListener {
    private MusicPlayer musicPlayer;
    private JSlider slider;
    private JLabel label;
    private VolumeSliderType volumeType;
    private boolean block;

    public VolumeSliderController(MusicPlayer musicPlayer, JSlider slider, JLabel label, VolumeSliderType volType) {
        this.musicPlayer = musicPlayer;
        this.slider = slider;
        this.label = label;
        this.volumeType = volType;
        block = false;

        slider.addChangeListener(this);
    }

    @Override
    public <E extends JmpEvent> void action(E event) {
        block = true;
        slider.setValue(musicPlayer.getCurrentSong().getVolumePercent());
    }

    @Override
    public void stateChanged(ChangeEvent event) {
        int newVolume = slider.getValue();

        label.setText(String.format("%03d %%", newVolume));

        if (block) {
            block = false;
            return;
        }
        if (slider.getValueIsAdjusting()) return;

        try {
            switch (volumeType) {
                case SONG_VOLUME -> musicPlayer.getCurrentSong().setVolumePercent(newVolume);
                case DEFAULT_VOLUME -> musicPlayer.setDefaultVolumePercent(newVolume);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
