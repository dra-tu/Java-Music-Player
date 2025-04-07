package gui.elements.mainArea.lists.SongButton;

import musicPlayer.MusicPlayer;
import musicPlayer.songTypes.Song;

import javax.swing.*;

public class SongButton extends JButton {
    public SongButton(Song song, MusicPlayer musicPlayer) {
        super(String.format("%2d: %s", song.SONG_ID + 1, song.getName()));



        new SongButtonController(this, musicPlayer, song.SONG_ID);
    }
}
