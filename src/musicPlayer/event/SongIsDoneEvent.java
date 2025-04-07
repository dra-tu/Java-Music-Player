package musicPlayer.event;

import musicPlayer.event.jmpEvent.JmpEvent;

/**
 * The Event is triggered when the current song has reached its end position
 */
public class SongIsDoneEvent extends JmpEvent {
    public void callListeners() {
        super.callListeners();
    }
}
