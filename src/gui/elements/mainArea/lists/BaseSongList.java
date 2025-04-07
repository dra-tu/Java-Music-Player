package gui.elements.mainArea.lists;

import gui.elements.mainArea.lists.SongButton.SongButton;
import musicPlayer.MusicPlayer;
import musicPlayer.songTypes.Song;

import javax.swing.*;
import java.awt.*;

public class BaseSongList extends JPanel {
    private final JLabel headlineLabel;
    private final JPanel scrollContent;
    private final MusicPlayer musicPlayer;
    private final static int SCROLL_SPEED = 16;

    private BaseSongList(MusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;

        // create empty scroll list
        scrollContent = new JPanel();
        scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS));
        JScrollPane scrollList = new JScrollPane(scrollContent);
        scrollList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollList.setPreferredSize(new Dimension(200, 475));

        scrollList.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);
        scrollList.getHorizontalScrollBar().setUnitIncrement(SCROLL_SPEED);

        headlineLabel = new JLabel("[HEADLINE HEAR]");

        setSize(200, 500);
        setPreferredSize(new Dimension(200, 500));

        add(headlineLabel);
        add(scrollList);
    }

    public BaseSongList(String headlineText, MusicPlayer musicPlayer) {
        this(musicPlayer);
        setHeadline(headlineText);
    }

    public void setHeadline(String text) {
        headlineLabel.setText(text);
    }

    public void clearSongs() {
        scrollContent.removeAll();
    }

    public void setSongs(Song[] songs) {
        clearSongs();

        for (Song song : songs) {
            addSongAt(-1, song);
        }
    }

    public void addSong(Song song) {
        addSongAt(-1, song);
    }

    public void addSongFirst(Song song) {
        addSongAt(0, song);
    }

    private void addSongAt(int index, Song song) {
        scrollContent.add(new SongButton(song, musicPlayer), index);
    }

    public JComponent getSongButton(int id) {
        return (JComponent) scrollContent.getComponent(id);
    }
}
