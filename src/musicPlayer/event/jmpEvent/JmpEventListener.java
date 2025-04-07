package musicPlayer.event.jmpEvent;

import java.util.EventListener;

public interface JmpEventListener extends EventListener {
    public<E extends JmpEvent> void action(E event);
}
