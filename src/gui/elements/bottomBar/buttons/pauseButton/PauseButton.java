package gui.elements.bottomBar.buttons.pauseButton;

import gui.elements.bottomBar.buttons.ButtonStyle;
import musicPlayer.MusicPlayer;

import javax.swing.*;

public class PauseButton extends JButton {
    public PauseButton(MusicPlayer musicPlayer) {
        new ButtonStyle(this, "Pause/Continue");
        new PauseButtonController(this, musicPlayer);
    }
}
