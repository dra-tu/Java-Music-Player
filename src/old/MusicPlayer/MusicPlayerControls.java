package old.MusicPlayer;

// TODO: Remove this dependency (This is supposed to bring function and not whit the old.Terminal.UI)
import old.Terminal.UI.TerminalMenuSong;

import old.Types.Song;
import old.Types.Time;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.io.IOException;

import java.util.Collections;
import java.util.Random;

public class MusicPlayerControls {

    MusicPlayer musicPlayer;
    private final Random rng = new Random();

    MusicPlayerControls(MusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;
    }

    // Song Controls
    public void stopSong() {
        musicPlayer.getCurrentSong().clip.stop();
    }

    public void continueSong() {
        musicPlayer.getCurrentSong().clip.start();
    }

    public void jumpSong(Time time) {
        // TODO: check if jumping out of main.Song
        musicPlayer.setSongPosition(time);
    }

    public void skipSongSec(Time time) {
        long currentTime = (musicPlayer.getCurrentSong().clip.getMicrosecondPosition() / 1000000) + (seconds + minutes * 60);
        long[] newTime = musicPlayer.convertSecondsToMinutesAndSeconds(currentTime);
        jumpSong(newTime[0], newTime[1]);
    }

    public void rewindSongSec(Time time) {
        Time currentTime = musicPlayer.getCurrentTime();
        jumpSong(Time.subtrsct(currentTime, time));
    }

    public void quitSong() {
        musicPlayer.getCurrentSong().clip.stop();
        musicPlayer.getCurrentSong().clip.setFramePosition(0);
        musicPlayer.currentSongNum = -1;
        musicPlayer.printMainMenuOptions();
    }

    public void loadSongs() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // TODO: add user feedback example: (5/10) songs loaded
        File[] songFiles = musicPlayer.rootDir.listFiles();
        musicPlayer.songs = new Song[songFiles.length];
        for (int i = 0; i < songFiles.length; i++) {
            musicPlayer.songs[i] = new Song(songFiles[i], i);
        }
    }

    public void playSong(int number, boolean loop) {
        if (loop) {
            musicPlayer.songs[number].clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            musicPlayer.songs[number].clip.start();
        }
        musicPlayer.currentSongNum = number;

        // TODO: check if it is working
        TerminalMenuSong songMenu = new TerminalMenuSong(false);
        Thread songMenuThread = new Thread(songMenu);
        songMenuThread.start();
    }

    // TODO: add function that user can control exit
    // TODO: add control for user to skip songControl or go back(history)
    // TODO: MAke work
    public void mixedPlay(){
        while (true) {
            if (musicPlayer.toPlay.isEmpty()) {
                Collections.addAll(musicPlayer.toPlay, musicPlayer.songs);
            }

            // select rand main.Song, Play it, remove it from list
            // TODO: the playSong Method "blocks" the other stuff from executing => it needs it own Thread or so
            // TODO: I Fink that I will putt the MenuControls in der own Threats
            int songNum = rng.nextInt(musicPlayer.toPlay.size());
            playSong(musicPlayer.toPlay.get(songNum).number, false);
            musicPlayer.toPlay.remove(songNum);

            // start Thread waiting for the main.Song
            CheckSongDone b = new CheckSongDone(musicPlayer.getCurrentSong().clip);
            Thread c = new Thread(b);
            c.start();

            // c.join();
        }
    }
}
