package gui.elements.bottomBar.buttons.pauseButton;

import gui.color.ColorMgr;
import gui.elements.jmp.JmpButton;
import gui.elements.bottomBar.buttons.ButtonStyle;
import musicPlayer.MusicPlayer;

public class PauseButton extends JmpButton {
    public PauseButton(ColorMgr colorMgr, MusicPlayer musicPlayer) {
        colorMgr.add(this);
        new ButtonStyle(this, "Pause/Continue");
        new PauseButtonController(this, musicPlayer);
    }
}
