package gui;

import gui.color.ColorMgr;
import gui.color.palettes.DefaultColorPalette;
import gui.elements.GuiFrame;

import gui.playMode.AutoPlay;
import musicPlayer.MusicPlayer;
import musicPlayer.PlayerStarter;

import java.io.IOException;

public class Gui extends PlayerStarter {
    static public final String RESOURCES_PATH = "/resources/";
    static public final String IMG_PATH = RESOURCES_PATH + "img/";
    private GuiFrame frame;

    @Override
    public boolean start(String dirPath, boolean startSong) {
        MusicPlayer musicPlayer = new MusicPlayer();
        try {
            musicPlayer.useDir(dirPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ColorMgr colMgr = new ColorMgr();

        frame = new GuiFrame(colMgr);
        frame.createGuiElements(musicPlayer);

        colMgr.add(frame);
        colMgr.changeColor(new DefaultColorPalette());

        new AutoPlay(musicPlayer, true);
        musicPlayer.startRandomSong();

        return true;
    }
}