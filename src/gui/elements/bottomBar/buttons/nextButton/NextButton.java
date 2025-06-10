package gui.elements.bottomBar.buttons.nextButton;

import gui.color.ColorMgr;
import gui.elements.jmp.JmpButton;
import gui.elements.bottomBar.buttons.ButtonStyle;
import musicPlayer.MusicPlayer;

public class NextButton extends JmpButton {
    public NextButton(ColorMgr colorMgr, MusicPlayer musicPlayer) {
        super();

        colorMgr.add(this);
        new ButtonStyle(this, "Next Song");
        new NextButtonController(this, musicPlayer);
    }
}
