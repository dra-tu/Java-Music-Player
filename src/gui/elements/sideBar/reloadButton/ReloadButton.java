package gui.elements.sideBar.reloadButton;

import gui.color.ColorMgr;
import gui.elements.GuiFrame;
import gui.elements.jmp.JmpButton;
import musicPlayer.MusicPlayer;

import java.io.IOException;

public class ReloadButton extends JmpButton {
    public ReloadButton(ColorMgr colorMgr, MusicPlayer musicPlayer, GuiFrame frame) {

        setText("Reload");
        colorMgr.add(this);

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
