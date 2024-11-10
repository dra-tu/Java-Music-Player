package MusicPlayer;

import MusicPlayer.Types.LoadedSong;
import MusicPlayer.Types.Song;
import MusicPlayer.Types.TimeStamp;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

public class MusicPlayer {
    File rootDir;
    Song[] songs;

    private LoadedSong currentSong;

    int maxLoadedSongs;
    HashMap<Integer, LoadedSong> loadedSongs;
    int nextOpenLoadHistorySlot;
    int[] loadHistory;

    public MusicPlayer(int maxLoadedSongs) {
        this.maxLoadedSongs = maxLoadedSongs;
        this.loadedSongs = new HashMap<>();
        this.loadHistory = new int[maxLoadedSongs];
        Arrays.fill(loadHistory, -1);
        this.nextOpenLoadHistorySlot = 0;
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
            out.append(" - (")
                    .append( String.format("%3d", song.getSongId())) // format of SONG_ID whit lIst in HomeMenu
                    .append(") ")
                    .append(song.getName())
                    .append("\n");
        }

        return out.toString();
    }

    public LoadedSong getCurrentSong() {
        return currentSong;
    }

    public int loadSong(int number) {

        if(loadedSongs.size() == maxLoadedSongs) shiftLoadedSongs();

        final int SONG_ID = songs[number].getSongId();

        // load Song and save it in HasMap
        LoadedSong song = new LoadedSong();
        boolean loadWorked = song.loadSong(songs[number]);
        if(!loadWorked) return -1; // can not load Song
        if(loadedSongs.containsKey(SONG_ID)) return 1; // can not load same Song two times
        loadedSongs.put(SONG_ID, song);

        // make reference to new Song in loadSlots Array
        loadHistory[nextOpenLoadHistorySlot] = SONG_ID;
        nextOpenLoadHistorySlot++;

        return 0;
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
        if(currentSong.getSongId() == songId) return;
        loadedSongs.remove(songId);
    }

    public boolean playSong(int songId) {
        if(!loadedSongs.containsKey(songId)) return false; // song not loaded

        loadedSongs.get(songId).start();

        currentSong = loadedSongs.get(songId);

        return true;
    }

    public void continueSong() {
        currentSong.start();
    }

    public void jumpTo(TimeStamp jumpTime) {
        currentSong.setTime(jumpTime);
    }

    public void skipTime(TimeStamp skipTime) {
        currentSong.setTime(
                TimeStamp.add(currentSong.getCurrentTime(), skipTime)
        );
    }

    public void rewindTime(TimeStamp rewindTime) {
        currentSong.setTime(
                TimeStamp.subtract(currentSong.getCurrentTime(), rewindTime)
        );
    }

    public void stopSong() {
        currentSong.stop();
    }

    public void exitSong() {
        currentSong.stop();
        currentSong.setTimeToStart();

        currentSong = null;
    }
}
