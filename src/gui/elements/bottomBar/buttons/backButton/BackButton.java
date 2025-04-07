package gui.elements.bottomBar.buttons.backButton;

import gui.elements.bottomBar.buttons.ButtonStyle;
import musicPlayer.MusicPlayer;

import javax.swing.*;

public class BackButton extends JButton {
    public BackButton(MusicPlayer musicPlayer) {
        super();

        new ButtonStyle(this, "Previous Song");

        new BackButtonController(this, musicPlayer);
    }
}
