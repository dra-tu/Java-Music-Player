package gui.elements.bottomBar.buttons.pauseButton;

import gui.elements.jmp.JmpButton;
import gui.elements.bottomBar.buttons.ButtonStyle;
import musicPlayer.MusicPlayer;

public class PauseButton extends JmpButton {
    public PauseButton(MusicPlayer musicPlayer) {
        new ButtonStyle(this, "Pause/Continue");
        new PauseButtonController(this, musicPlayer);
    }
}
