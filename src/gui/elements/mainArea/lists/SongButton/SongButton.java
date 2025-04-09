package gui.elements.mainArea.lists.SongButton;

import musicPlayer.MusicPlayer;
import musicPlayer.songTypes.Song;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SongButton extends JButton implements ActionListener {
    private final MusicPlayer musicPlayer;
    private final int SONG_ID;

    public SongButton(Song song, MusicPlayer musicPlayer) {
        super(String.format("%3d: %s", song.SONG_ID + 1, song.getName()));
        this.musicPlayer = musicPlayer;
        this.SONG_ID = song.SONG_ID;

        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        musicPlayer.startSong(SONG_ID);
    }
}
