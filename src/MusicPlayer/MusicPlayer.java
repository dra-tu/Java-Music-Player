package MusicPlayer;

import MusicPlayer.Types.LoadedSong;
import MusicPlayer.Types.Song;

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

    public Song[] getSongs() {
        return songs;
    }

    public LoadedSong getCurrentSong() {
        return currentSong;
    }

    public int loadSong(int songId) {
        if(songId > songs.length || songId < 0) return -1; // can not load Song

        if(loadedSongs.size() == maxLoadedSongs) shiftLoadedSongs();

        // load Song and save it in HasMap
        LoadedSong song = new LoadedSong();
        boolean loadWorked = song.loadSong(songs[songId]);
        if(!loadWorked) return -1; // can not load Song
        if(loadedSongs.containsKey(songId)) return 1; // can not load same Song two times
        loadedSongs.put(songId, song);

        // make reference to new Song in loadSlots Array
        loadHistory[nextOpenLoadHistorySlot] = songId;
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
        if(currentSong.getSongId() == songId) return;

        loadedSongs.get(songId).unload();
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

    public void jumpTo(long jumpTime) {
        currentSong.setTime(jumpTime);
    }

    public void skipTime(long skipTime) {
        currentSong.setTime(
                currentSong.getCurrentTime() + skipTime
        );
    }

    public void rewindTime(long rewindTime) {
        currentSong.setTime(
                currentSong.getCurrentTime() - rewindTime
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
