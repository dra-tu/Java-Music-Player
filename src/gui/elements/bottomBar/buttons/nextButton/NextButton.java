package gui.elements.bottomBar.buttons.nextButton;

import gui.elements.jmp.JmpButton;
import gui.elements.bottomBar.buttons.ButtonStyle;
import musicPlayer.MusicPlayer;

public class NextButton extends JmpButton {
    public NextButton(MusicPlayer musicPlayer) {
        super();

        new ButtonStyle(this, "Next Song");

        new NextButtonController(this, musicPlayer);
    }
}
