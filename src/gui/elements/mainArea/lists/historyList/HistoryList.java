package gui.elements.mainArea.lists.historyList;

import gui.color.ColorMgr;
import gui.elements.mainArea.lists.BaseSongList;
import gui.elements.mainArea.lists.SongButton.SongButton;
import musicPlayer.MusicPlayer;
import musicPlayer.event.HistoryPointerChangedEvent;
import musicPlayer.event.NewHistoryEntryEvent;
import musicPlayer.event.jmpEvent.JmpEvent;
import musicPlayer.event.jmpEvent.JmpEventListener;

public class HistoryList extends BaseSongList implements JmpEventListener {
    private final MusicPlayer musicPlayer;

    private SongButton lastStyle;

    public HistoryList(ColorMgr colorMgr, MusicPlayer musicPlayer) {
        super("History list", colorMgr, musicPlayer);
        this.musicPlayer = musicPlayer;
        reload();
        musicPlayer.addEventListener(NewHistoryEntryEvent.class, this);
        musicPlayer.addEventListener(HistoryPointerChangedEvent.class, this);
    }

    private void reload() {
        clearSongs();

        Integer[] history = musicPlayer.getHistory();
        for (Integer songId : history) {
            addSong(musicPlayer.getSong(songId));
        }
    }

    @Override
    public <E extends JmpEvent> void action(E event) {
        if (event instanceof NewHistoryEntryEvent) {
            NewHistoryEntryEvent historyEvent = (NewHistoryEntryEvent) event;
            addSongFirst(historyEvent.getNewSong());
        } else if (event instanceof HistoryPointerChangedEvent) {
            HistoryPointerChangedEvent historyEvent = (HistoryPointerChangedEvent) event;

            if (lastStyle != null) lastStyle.unSelect();
//                SelectedStyle.removeStyle(lastStyle);

            int pointer = historyEvent.pointerPosition();

            if (pointer == -1) {
                lastStyle = null;
                return;
            }

            lastStyle = getSongButton(pointer);
//            SelectedStyle.applyStyle(lastStyle);
            lastStyle.select();
        }

    }
}
