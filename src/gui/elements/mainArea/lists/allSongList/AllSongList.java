package gui.elements.mainArea.lists.allSongList;

import gui.color.ColorMgr;
import gui.elements.mainArea.lists.BaseSongList;
import musicPlayer.MusicPlayer;

public class AllSongList extends BaseSongList {
    public AllSongList(ColorMgr colorMgr, MusicPlayer musicPlayer) {
        super("All Songs", colorMgr, musicPlayer);

        new AllSongListController(musicPlayer, this);
    }
}
