package musicPlayer.event;

import musicPlayer.event.jmpEvent.JmpEvent;

public class HistoryPointerChangedEvent extends JmpEvent {
    private int pointerPosition;

    public int pointerPosition() {
        return pointerPosition;
    }

    public void callListeners(int pointerPosition) {
        this.pointerPosition = pointerPosition;
        super.callListeners();
    }
}
