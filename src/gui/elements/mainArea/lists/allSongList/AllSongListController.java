package gui.elements.mainArea.lists.allSongList;

import gui.elements.mainArea.lists.SongButton;
import musicPlayer.MusicPlayer;
import musicPlayer.event.SongLoadedEvent;
import musicPlayer.event.jmpEvent.JmpEvent;
import musicPlayer.event.jmpEvent.JmpEventListener;
import musicPlayer.songTypes.Song;

class AllSongListController implements JmpEventListener {
    private final MusicPlayer musicPlayer;
    private final AllSongList list;
    private SongButton styledButton;

    AllSongListController(MusicPlayer musicPlayer, AllSongList list) {
        this.musicPlayer = musicPlayer;
        this.list = list;

        musicPlayer.addEventListener(SongLoadedEvent.class, this);
        fillList();
    }

    private void fillList() {
        Song[] songs = musicPlayer.getSongs();
        if (songs == null) {
            System.out.println("[Warning] NO SONGS");
            return;
        }
        list.setSongs(songs);
    }

    @Override
    public <E extends JmpEvent> void action(E event) {
        SongLoadedEvent loadedEvent = (SongLoadedEvent) event;

        if (styledButton != null) {
            styledButton.unSelect();
//            SelectedStyle.removeStyle(styledButton);
        }

        styledButton = list.getSongButton(loadedEvent.getNewSong().getSongId());

        styledButton.select();
//   JComponent     SelectedStyle.applyStyle(styledButton);
    }
}
