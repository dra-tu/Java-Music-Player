package gui.elements.mainArea.lists.allSongList;

import gui.elements.mainArea.lists.BaseSongList;
import musicPlayer.MusicPlayer;

public class AllSongList extends BaseSongList {
    public AllSongList(MusicPlayer musicPlayer) {
        super("All Songs", musicPlayer);

        new AllSongListController(musicPlayer, this);
    }
}
