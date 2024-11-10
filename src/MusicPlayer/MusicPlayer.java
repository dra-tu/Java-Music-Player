package MusicPlayer;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

public class MusicPlayer {
    File rootDir;
    Song[] songs;

    private LoadedSong currentSong;
    MusicPlayerState state;

    int maxLoadedSongs;
    HashMap<Integer, LoadedSong> loadedSongs;
    int nextOpenLoadHistorySlot;
    int[] loadHistory;

    public MusicPlayer(int maxLoadedSongs) {
        this.maxLoadedSongs = maxLoadedSongs;
        this.loadedSongs = new HashMap<Integer, LoadedSong>();
        this.loadHistory = new int[maxLoadedSongs];
        Arrays.fill(loadHistory, -1);
        this.nextOpenLoadHistorySlot = 0;
        this.state = MusicPlayerState.IDLE;
    }

    public boolean useDir(String dirPath) {
        File dir = new File(dirPath);
        if(!dir.isDirectory()) return false;
        rootDir = dir;

        File[] files = rootDir.listFiles(new WavFilter());
        if(files == null) return false;

        songs = new Song[files.length];
        for(int i = 0; i < files.length; i++) {
            songs[i] = new Song(i);
            boolean fileSetWorked = songs[i].setFile(files[i]);
            if(!fileSetWorked) return false;
        }

        // reset values
        nextOpenLoadHistorySlot = 0;
        Arrays.fill(loadHistory, -1);
        loadedSongs.clear();
        rootDir = dir;

        return true;
    }

    public boolean reloadDir() {
        return useDir(rootDir.getPath());
    }

    public String getSongList() {
        StringBuilder out = new StringBuilder();

        for(Song song: songs) {
            out.append(" - ").append("(").append( String.format("%03d", song.SONG_ID)).append(") ").append(song.name).append("\n");
        }

        return out.toString();
    }

    public LoadedSong getCurrentSong() {
        return currentSong;
    }

    public boolean loadSong(int number) {

        if(loadedSongs.size() == maxLoadedSongs) shiftLoadedSongs();

        final int SONG_ID = songs[number].SONG_ID;

        // load Song and save it in HasMap
        LoadedSong song = new LoadedSong();
        boolean loadWorked = song.loadSong(songs[number]);
        if(!loadWorked) return false; // can not load Song
        if(loadedSongs.containsKey(SONG_ID)) return false; // can not load same Song two times
        loadedSongs.put(SONG_ID, song);

        // make reference to new Song in loadSlots Array
        loadHistory[nextOpenLoadHistorySlot] = SONG_ID;
        nextOpenLoadHistorySlot++;

        return true;
    }

    private void shiftLoadedSongs() {
        // remove HasMap entry
        if(loadHistory[0] != -1) unloadSong(loadHistory[0]);

        // removing entry from loadSlots
        for(int i = 0; i < maxLoadedSongs - 1; i++) {
            loadHistory[i] = loadHistory[i + 1];
        }
        loadHistory[maxLoadedSongs-1] = -1;

        // degrees nextOpenLoadSlot
        nextOpenLoadHistorySlot = Math.max(nextOpenLoadHistorySlot - 1, 0);
    }

    public void unloadSong(int songId) {
        // TODO: add return feedback
        if(currentSong.SONG_ID == songId) return;
        loadedSongs.remove(songId);
    }

    public boolean playSong(int songId) {
        if(!loadedSongs.containsKey(songId)) return false; // song not loaded

        loadedSongs.get(songId).clip.start();

        currentSong = loadedSongs.get(songId);
        state = MusicPlayerState.PLAYING;

        return true;
    }

    public void continueSong() {
        currentSong.clip.start();
        state = MusicPlayerState.PLAYING;
    }

    public void jumpTo(TimeStamp jumpTime) {
        currentSong.clip.setMicrosecondPosition(jumpTime.toMicroseconds());
    }

    public void skipTime(TimeStamp skipTime) {
        currentSong.clip.setMicrosecondPosition(
                currentSong.clip.getMicrosecondPosition() + skipTime.toMicroseconds()
        );
    }

    public void rewindTime(TimeStamp rewindTime) {
        currentSong.clip.setMicrosecondPosition(
                currentSong.clip.getMicrosecondPosition() - rewindTime.toMicroseconds()
        );
    }

    public void stopSong() {
        currentSong.clip.stop();
        state = MusicPlayerState.STOPPED;
    }

    public void exitSong() {
        currentSong.clip.stop();
        currentSong.clip.setMicrosecondPosition(0L);

        currentSong = null;
        state = MusicPlayerState.IDLE;
    }
}
