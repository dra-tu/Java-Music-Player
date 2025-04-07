package musicPlayer.event;

import musicPlayer.event.jmpEvent.JmpEvent;
import musicPlayer.songTypes.Song;

public class NewHistoryEntryEvent extends JmpEvent {
    private Song newSong;

    private void setNewSong(Song newSong) {
        this.newSong = newSong;
    }

    public Song getNewSong() {
        return newSong;
    }

    public void callListeners(Song newSong) {
        setNewSong(newSong);
        super.callListeners();
    }
}
