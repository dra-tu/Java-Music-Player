package gui.elements.mainArea.lists.historyList;

import gui.elements.mainArea.lists.BaseSongList;
import gui.elements.mainArea.lists.SongButton.style.SelectedStyle;
import musicPlayer.MusicPlayer;
import musicPlayer.event.HistoryPointerChangedEvent;
import musicPlayer.event.NewHistoryEntryEvent;
import musicPlayer.event.jmpEvent.JmpEvent;
import musicPlayer.event.jmpEvent.JmpEventListener;

import javax.swing.*;

public class HistoryList extends BaseSongList implements JmpEventListener {
    private final MusicPlayer musicPlayer;

    private JComponent lastStyle;

    public HistoryList(MusicPlayer musicPlayer) {
        super("History list", musicPlayer);
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

            if (lastStyle != null) SelectedStyle.removeStyle(lastStyle);

            int pointer = historyEvent.pointerPosition();

            if (pointer == -1) {
                lastStyle = null;
                return;
            }

            lastStyle = getSongButton(pointer);
            SelectedStyle.applyStyle(lastStyle);
        }

    }
}
