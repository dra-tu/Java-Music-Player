package gui.elements.sideBar.reloadButton;

import gui.elements.GuiFrame;
import musicPlayer.MusicPlayer;

import javax.swing.*;
import java.io.IOException;

public class ReloadButton extends JButton {
    public ReloadButton(MusicPlayer musicPlayer, GuiFrame frame) {

        setText("Reload");

        this.addActionListener((event) -> {
            try {
                musicPlayer.reloadDir();
                frame.removeGuiElements();
                frame.createGuiElements(musicPlayer);
                musicPlayer.startRandomSong();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
