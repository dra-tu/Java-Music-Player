package gui.elements.bottomBar.buttons.backButton;

import gui.color.ColorMgr;
import gui.elements.jmp.JmpButton;
import gui.elements.bottomBar.buttons.ButtonStyle;
import musicPlayer.MusicPlayer;

public class BackButton extends JmpButton {
    public BackButton(ColorMgr colorMgr, MusicPlayer musicPlayer) {
        super();

        colorMgr.add(this);
        new ButtonStyle(this, "Previous Song");
        new BackButtonController(this, musicPlayer);
    }
}
