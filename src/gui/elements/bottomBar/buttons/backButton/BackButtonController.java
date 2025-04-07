package gui.elements.bottomBar.buttons.backButton;

import musicPlayer.MusicPlayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class BackButtonController implements ActionListener {
    private final MusicPlayer musicPlayer;

    BackButtonController(JButton button, MusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;

        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            musicPlayer.exitSong();
            musicPlayer.historyBack(1);
            musicPlayer.historyLoadSong();
            musicPlayer.continueSong();
        } catch (IOException ex) {
            System.out.println("NOOOOOOOOOOO");
        }
    }
}
