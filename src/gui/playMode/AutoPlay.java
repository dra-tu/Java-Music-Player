package gui.playMode;

import musicPlayer.MusicPlayer;
import musicPlayer.event.SongIsDoneEvent;
import musicPlayer.event.jmpEvent.JmpEvent;
import musicPlayer.event.jmpEvent.JmpEventListener;

import java.io.IOException;

public class AutoPlay implements JmpEventListener {
    private MusicPlayer musicPlayer;
    private boolean active;

    public AutoPlay(MusicPlayer musicPlayer, boolean start) {
        musicPlayer.addEventListener(SongIsDoneEvent.class, this);
        this.musicPlayer = musicPlayer;
        this.active = start;
    }

    @Override
    public <E extends JmpEvent> void action(E event) {
        if (!active) return;

        musicPlayer.startRandomSong();
    }
}
