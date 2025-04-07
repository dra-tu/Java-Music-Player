package gui.elements.mainArea.lists.SongButton;

import musicPlayer.MusicPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SongButtonController implements ActionListener {
    private final MusicPlayer musicPlayer;
    private final int SONG_ID;

    SongButtonController(SongButton button, MusicPlayer musicPlayer, int songId) {
        this.musicPlayer = musicPlayer;
        this.SONG_ID = songId;

        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        musicPlayer.startSong(SONG_ID);
    }
}
