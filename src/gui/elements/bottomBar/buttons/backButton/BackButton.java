package gui.elements.bottomBar.buttons.backButton;

import gui.elements.jmp.JmpButton;
import gui.elements.bottomBar.buttons.ButtonStyle;
import musicPlayer.MusicPlayer;

public class BackButton extends JmpButton {
    public BackButton(MusicPlayer musicPlayer) {
        super();

        new ButtonStyle(this, "Previous Song");

        new BackButtonController(this, musicPlayer);
    }
}
