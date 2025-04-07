package gui.elements.bottomBar.buttons.nextButton;

import musicPlayer.MusicPlayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class NextButtonController implements ActionListener {
    private final MusicPlayer musicPlayer;

    NextButtonController(JButton button, MusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;

        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            musicPlayer.exitSong();
            musicPlayer.historyNext(1);
            musicPlayer.historyLoadSong();
            musicPlayer.continueSong();
        } catch (IOException ex) {
            System.out.println("NOOOOOOOOOOO");
        }
    }
}
