package musicPlayer.event;

import musicPlayer.event.jmpEvent.JmpEvent;
import musicPlayer.songTypes.LoadedSong;

public class SongLoadedEvent extends JmpEvent {
    private LoadedSong newSong;

    private void setNewSong(LoadedSong newSong) {
        this.newSong = newSong;
    }

    public LoadedSong getNewSong() {
        return newSong;
    }

    public void callListeners(LoadedSong newSong) {
        setNewSong(newSong);
        super.callListeners();
    }
}
