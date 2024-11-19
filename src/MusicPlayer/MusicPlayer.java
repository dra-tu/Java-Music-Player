package MusicPlayer;

import MusicPlayer.Types.LoadedSong;
import MusicPlayer.Types.Song;

import java.io.File;

/**
 * A wrapper for javax.sound.sampled.Clip, whit more functionality. Examples: jump Methods, supports multiple Songs/Clips.
 */
public class MusicPlayer {
    /**
     * the directory with all the Song files
     */
    private File rootDir;
    /**
     * An Array with all the songs from rootDir
     */
    private Song[] songs;
    /**
     * The currently playing Song
     */
    private LoadedSong currentSong;

    /**
     * Creates a new MusicPlayer
     */
    public MusicPlayer() {
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
        if(currentSong != null) {
            if (currentSong.getSongId() == songId) return 1; // will not load same Song two times
        }

        LoadedSong song = new LoadedSong();
        boolean loadWorked = song.loadSong(songs[songId]);
        if(!loadWorked) return -1; // can not load Song
        currentSong = song;

        return 0;
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
     * @return FALSE if there is no song to continue else TRUE
     */
    public boolean continueSong() {
        if(currentSong == null) return false; // no song to continue

        currentSong.start();
        return true;
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
     */
    public void rewindTime(long rewindTime){
        if(currentSong == null) return;

        currentSong.setTime(
                currentSong.getCurrentTime() - rewindTime
        );
    }

    /**
     * Stops the currentSong and then sets the currentSong to null.
     */
    public void exitSong(){
        if(currentSong == null) return;

        currentSong.exit();
        currentSong = null;
    }
}
