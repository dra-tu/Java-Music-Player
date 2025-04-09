package gui.elements.mainArea.lists;

import gui.color.ColorMgr;
import gui.color.JmpColored;
import gui.color.JmpGuiColorPalette;
import musicPlayer.MusicPlayer;
import musicPlayer.songTypes.Song;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SongButton extends JButton implements ActionListener, JmpColored {
    private final MusicPlayer musicPlayer;
    private final int SONG_ID;

    private int state;
    private static final int COLORS_LENGTH = 2;
    private static Color[] colors = new Color[COLORS_LENGTH];

    public SongButton(Song song, ColorMgr colorMgr, MusicPlayer musicPlayer) {
        super(String.format("%d: %s", song.SONG_ID + 1, song.getName()));
        this.musicPlayer = musicPlayer;
        this.SONG_ID = song.SONG_ID;

        state = 0;
        colorMgr.add(this);

        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        musicPlayer.startSong(SONG_ID);
    }

    @Override
    public <CP extends JmpGuiColorPalette> void updateColors(CP cp) {
        System.out.println("change color pallet - Button");
        colors[0] = cp.getButtonNormal();
        colors[1] = cp.getButtonSelected();

        this.setBackground(colors[state]);
    }

    public <C extends JComponent> void unSelect() {
        state = 0;
        setBackground(colors[state]);
        System.out.println("unSelect - " + SONG_ID);
    }

    public <C extends JComponent> void select() {
        state = 1;
        setBackground(colors[state]);
        System.out.println("Select + " + SONG_ID);
    }
}