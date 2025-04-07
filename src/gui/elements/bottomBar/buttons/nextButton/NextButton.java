package gui.elements.bottomBar.buttons.nextButton;

import gui.elements.bottomBar.buttons.ButtonStyle;
import musicPlayer.MusicPlayer;

import javax.swing.*;

public class NextButton extends JButton {
    public NextButton(MusicPlayer musicPlayer) {
        super();

        new ButtonStyle(this, "Next Song");

        new NextButtonController(this, musicPlayer);
    }
}
