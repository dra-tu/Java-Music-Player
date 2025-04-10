package gui.elements.mainArea.lists;

import gui.color.ColorMgr;
import gui.elements.jmp.JmpButton;
import gui.elements.jmp.JmpButtonConstant;
import musicPlayer.MusicPlayer;
import musicPlayer.songTypes.Song;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SongButton extends JmpButton implements ActionListener {
    private final MusicPlayer musicPlayer;
    private final int SONG_ID;

    public SongButton(Song song, ColorMgr colorMgr, MusicPlayer musicPlayer) {
        setText(String.format("%d: %s", song.SONG_ID + 1, song.getName()));
        this.musicPlayer = musicPlayer;
        this.SONG_ID = song.SONG_ID;

        colorMgr.add(this);

        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        musicPlayer.startSong(SONG_ID);
    }

    public <C extends JComponent> void unSelect() {
        setState(JmpButtonConstant.State.NORMAL);
    }

    public <C extends JComponent> void select() {
        setState(JmpButtonConstant.State.SONG_HIGHLIGHT);
    }
}