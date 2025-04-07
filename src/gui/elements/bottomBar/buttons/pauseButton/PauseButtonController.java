package gui.elements.bottomBar.buttons.pauseButton;

import musicPlayer.MusicPlayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class PauseButtonController implements ActionListener {
    boolean musicIsPaused;
    private final MusicPlayer musicPlayer;

    PauseButtonController(JButton button, MusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;
        musicIsPaused = false;

        button.setMnemonic(KeyEvent.VK_SPACE);
        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (musicIsPaused) {
            musicPlayer.continueSong();
            musicIsPaused = false;
        }else {
            musicPlayer.stopSong();
            musicIsPaused = true;
        }
    }
}
