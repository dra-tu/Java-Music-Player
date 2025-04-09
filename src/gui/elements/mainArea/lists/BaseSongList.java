package gui.elements.mainArea.lists;

import gui.color.ColorMgr;
import musicPlayer.MusicPlayer;
import musicPlayer.songTypes.Song;

import javax.swing.*;
import java.awt.*;

public class BaseSongList extends JPanel {
    private final JLabel headlineLabel;
    private final JPanel scrollContent;
    private final ColorMgr colorMgr;
    private final MusicPlayer musicPlayer;
    private final static int SCROLL_SPEED = 16;
    private final static Dimension SIZE = new Dimension(200, 470);
    private final static Dimension SCROLL_SIZE = new Dimension(200, 445);

    public BaseSongList(String headlineText, ColorMgr colorMgr, MusicPlayer musicPlayer) {
        this.colorMgr = colorMgr;
        this.musicPlayer = musicPlayer;

        // create empty scroll list
        scrollContent = new JPanel();
        scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS));
        JScrollPane scrollList = new JScrollPane(scrollContent);
        scrollList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollList.setPreferredSize(SCROLL_SIZE);

        scrollList.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);
        scrollList.getHorizontalScrollBar().setUnitIncrement(SCROLL_SPEED);

        headlineLabel = new JLabel(headlineText);

        setSize(SIZE);
        setPreferredSize(SIZE);

        add(headlineLabel);
        add(scrollList);
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
        scrollContent.add(new SongButton(song, colorMgr, musicPlayer), index);
    }

    public SongButton getSongButton(int id) {
        return (SongButton) scrollContent.getComponent(id);
    }
}
