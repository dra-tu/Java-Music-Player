package gui;

import gui.color.ColorMgr;
import gui.color.palettes.DefaultColorPalette;
import gui.color.palettes.NewColorPalette;
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
    public boolean start(String dirPath) {
        MusicPlayer musicPlayer = new MusicPlayer();
        try {
            musicPlayer.useDir(dirPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ColorMgr colMgr = new ColorMgr();
        colMgr.addPaletteToList(new DefaultColorPalette());
        colMgr.addPaletteToList(new NewColorPalette());
        colMgr.changeColor("Default");

        frame = new GuiFrame(colMgr);
        colMgr.add(frame);

        frame.createGuiElements(musicPlayer);

        new AutoPlay(musicPlayer, true);
        musicPlayer.startRandomSong();

        return true;
    }
}