package MusicPlayer;

import MusicPlayer.Types.LoadedSong;
import MusicPlayer.Types.Song;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

/**
 * A wrapper for javax.sound.sampled.Clip, whit more functionality. Examples: jump Methods, supports multiple Songs/Clips.
 */
public class MusicPlayer {
    /**
     * the directory with all the Song files
     */
    File rootDir;
    /**
     * An Array with all the songs from rootDir
     */
    Song[] songs;

    /**
     * The currently playing Song
     */
    private LoadedSong currentSong;

    /**
     * This is equivalent to loadHistory.Length
     */
    int maxLoadedSongs;
    /**
     * All the loaded Songs the Key is the SongID
     */
    HashMap<Integer, LoadedSong> loadedSongs;
    /**
     * The next index of the loadHistory Array without a value
     */
    int nextOpenLoadHistorySlot;
    /**
     * An Array sorted by the time a Song got loaded, the newest has the biggest index.
     */
    int[] loadHistory;

    /**
     * Creates a new MusicPlayer
     * @param maxLoadedSongs the max loaded Songs increasing this may result in higher ram uses and less cpu or reversed if decreased.
     */
    public MusicPlayer(int maxLoadedSongs) {
        this.maxLoadedSongs = maxLoadedSongs;
        this.loadedSongs = new HashMap<>();
        this.loadHistory = new int[maxLoadedSongs];
        Arrays.fill(loadHistory, -1);
        this.nextOpenLoadHistorySlot = 0;
    }

    /**
     * sets rootDir to dirPath and overwrites the songs list with the Song in dirPath.
     * All loaded Songs get unloaded.
     * @param dirPath A Path to a Directory containing the songs in the .wav format
     * @return boolean: true if the dirPath is loaded,
     *         false if dirPath can not be loaded
     */
    public boolean useDir(String dirPath) {
        File dir = new File(dirPath);
        if(!dir.isDirectory()) return false; // not a dir
        rootDir = dir;

        File[] files = rootDir.listFiles(new WavFilter());
        if(files == null) return false; // no wav files

        songs = new Song[files.length];
        for(int i = 0; i < files.length; i++) {
            songs[i] = new Song(i);
            boolean fileSetWorked = songs[i].setFile(files[i]);
            if(!fileSetWorked) return false; // dir contains dir with the ending ".wav"
        }

        // reset values
        nextOpenLoadHistorySlot = 0;
        Arrays.fill(loadHistory, -1);
        loadedSongs.clear();
        rootDir = dir;

        return true;
    }

    /**
     * Same as useDir(rootDir)
     * @return boolean: true if dir is reloaded,
     *         false if dir can not be reloaded
     */
    public boolean reloadDir() {
        return useDir(rootDir.getPath());
    }

    /**
     * Get all songs the MusicPlayer knows.
     * A Song has a name, SONG_ID and file
     * @return Song[]: Alias to songs
     */
    public Song[] getSongs() {
        return songs;
    }

    /**
     * Get the Song (as a LoadedSong) which is currently playing
     * @return LoadedSong: currentSong
     */
    public LoadedSong getCurrentSong() {
        return currentSong;
    }

    /**
     * loading a Song is required before playing it
     * @param songId int: the songId of the song to load
     * @return int: -2 if the song can not be loaded, -1 if the songId is invalid,
     * 1 if the song is already loaded or 0 if the song is now loaded
     */
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

    /**
     * unloads the oldest loaded song
     */
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

    /**
     * Unloads the song with the given songID
     * @param songId songID to be unloaded
     */
    public void unloadSong(int songId) {
        if(currentSong != null) {
            if (currentSong.getSongId() == songId) return;
        }

        loadedSongs.get(songId).unload();
        loadedSongs.remove(songId);
    }

    /**
     * Start the Playback of the Song with the given songID.
     * @param songId the Song to be played
     * @return false if the Song is not loaded, true if the Song is now playing.
     */
    public boolean playSong(int songId) {
        if(!loadedSongs.containsKey(songId)) return false; // song not loaded

        loadedSongs.get(songId).start();

        currentSong = loadedSongs.get(songId);

        return true;
    }

    /**
     * Pauses the currently playing Song
     * If the currentSong is stopped noting happens.
     * @throws NullPointerException if there is no currentSong
     */
    public void stopSong() throws NullPointerException {
        currentSong.stop();
    }

    /**
     * continues playing after a Song got stopped from stopSong().
     * If the currentSong is not stopped noting happens.
     * @throws NullPointerException if there is no currentSong
     */
    public void continueSong() throws NullPointerException {
        currentSong.start();
    }

    /**
     * Sets the time position of the current Song to jumpTime
     * @param jumpTime the time to jump to in microseconds
     * @throws NullPointerException if there is no currentSong
     */
    public void jumpTo(long jumpTime) throws NullPointerException {
        currentSong.setTime(jumpTime);
    }

    /**
     * Lets you jump forward in the Song. This works by adding the skipTime to the currentTime and then jump to the new time.
     * @param skipTime time to skip forward in microseconds
     * @throws NullPointerException if there is no currentSong
     */
    public void skipTime(long skipTime) throws NullPointerException {
        currentSong.setTime(
                currentSong.getCurrentTime() + skipTime
        );
    }

    /**
     * Lets you jump backwards in the Song. This works by subtracting the rewindTime from the currentTime and then jump to the new time.
     * @param rewindTime time to rewind in microseconds
     * @throws NullPointerException if there is no currentSong
     */
    public void rewindTime(long rewindTime) throws NullPointerException {
        currentSong.setTime(
                currentSong.getCurrentTime() - rewindTime
        );
    }

    /**
     * Stops the currentSong and then sets the currentSong to null.
     * @throws NullPointerException if there is no currentSong
     */
    public void exitSong() throws NullPointerException {
        currentSong.stop();
        currentSong.setTimeToStart();

        currentSong = null;
    }
}
