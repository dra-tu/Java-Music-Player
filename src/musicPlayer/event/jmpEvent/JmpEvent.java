package musicPlayer.event.jmpEvent;

import java.util.ArrayList;

abstract public class JmpEvent {
    private final ArrayList<JmpEventListener> listenerList;

    protected JmpEvent() {
        listenerList = new ArrayList<>();
    }

    public boolean removeListener(JmpEventListener oldListener) {
        return listenerList.remove(oldListener);
    }

    public void addListener(JmpEventListener newListener) {
        listenerList.add(newListener);
    }

    protected void callListeners() {
        for (JmpEventListener listener: listenerList) {
            listener.action(this);
        }
    }
}
