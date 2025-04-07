package musicPlayer;

import musicPlayer.event.HistoryPointerChangedEvent;
import musicPlayer.event.SongIsDoneEvent;
import musicPlayer.event.SongLoadedEvent;
import musicPlayer.event.jmpEvent.JmpEvent;
import musicPlayer.parser.configTypes.Config;
import musicPlayer.parser.configTypes.SongConfig;
import musicPlayer.event.NewHistoryEntryEvent;
import musicPlayer.event.jmpEvent.JmpEventListener;
import musicPlayer.songTypes.LoadedSong;
import musicPlayer.songTypes.Song;
import musicPlayer.timerTask.TimerTaskSongCheck;

import java.io.*;

import java.nio.file.Path;
import java.util.*;

/**
 * A wrapper for javax.sound.sampled.Clip, whit more functionality. Examples: jump Methods, supports multiple Songs/Clips.
 */
public class MusicPlayer {
    Random rng;

    int SONG_COUNT;

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

    private int historyPointer;
    private ArrayList<Integer> history;

    public static final int HISTORY_MAX_SIZE = 500;

    // config stuff
    private static final String confDirName = ".jmpConf";
    private static final String globalConfName = ".global";
    private Config config;

    private File confDir;

    // Events
    private HashMap<Class<?>, JmpEvent> events;

    /**
     * Creates a new MusicPlayer
     */
    public MusicPlayer() {
        rng = new Random();

        // Events
        events = new HashMap<>();
        events.put(NewHistoryEntryEvent.class, new NewHistoryEntryEvent());
        events.put(SongLoadedEvent.class, new SongLoadedEvent());
        events.put(SongIsDoneEvent.class, new SongIsDoneEvent());
        events.put(HistoryPointerChangedEvent.class, new HistoryPointerChangedEvent());

        timerSetUp();
    }

    /**
     * Timer to check for Song Events
     */
    private void timerSetUp() {
        TimerTask timeUpdater = new TimerTaskSongCheck(this, (SongIsDoneEvent) getEvent(SongIsDoneEvent.class));

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timeUpdater, 0, 1_000);
    }

    /**
     * sets rootDir to dirPath and overwrites the songs list with the Song in dirPath.
     * All loaded Songs get unloaded.
     *
     * @param dirPath A Path to a Directory containing the songs in the .wav format
     * @return boolean: true if the dirPath is loaded,
     * false if dirPath can not be loaded
     */
    public boolean useDir(String dirPath) throws IOException {
        File dir = new File(dirPath);
        if (!dir.isDirectory()) return false; // not a dir
        rootDir = dir;

        // get wav files
        File[] files = rootDir.listFiles(new WavFilter());
        if (files == null) return false; // no wav files

        songs = new Song[files.length];
        for (int i = 0; i < files.length; i++) {
            songs[i] = new Song(i);
            boolean fileSetWorked = songs[i].setFile(files[i]);
            if (!fileSetWorked) return false; // dir contains dir with the ending ".wav"
        }

        // set values
        rootDir = dir;
        confDir = Path.of(rootDir.getAbsolutePath(), confDirName).toFile();
        confDir.mkdir();
        config = new Config(Path.of(rootDir.getAbsolutePath(), globalConfName).toFile());
        SONG_COUNT = songs.length;
        history = new ArrayList<>();
        setHistoryPointer(-1);
        exitSong();

        SongConfig.setGlobalConfig(config);

        return true;
    }

    public <E extends JmpEvent> void addEventListener(Class<E> eventClass, JmpEventListener newListener) {
        JmpEvent event = getEvent(eventClass);
        event.addListener(newListener);
    }

    public <E extends JmpEvent> void removeEventListener(Class<E> eventClass, JmpEventListener newListener) {
        JmpEvent event = getEvent(eventClass);
        event.removeListener(newListener);
    }

    private <E extends JmpEvent> JmpEvent getEvent(Class<E> eventClass) {
        JmpEvent event = events.get(eventClass);

        if (event == null || event.getClass() != eventClass){
            throw new IllegalArgumentException("The MusicPlayer has no " + eventClass.getSimpleName());
        }

        return event;
    }


    public boolean setDefaultVolumePercent(int volume) throws IOException {
        return config.setDefaultVolumePercent(volume);
    }

    /**
     * Same as useDir(rootDir)
     *
     * @return boolean: true if dir is reloaded,
     * false if dir can not be reloaded
     */
    public boolean reloadDir() throws IOException {
        return useDir(rootDir.getPath());
    }

    /**
     * Get all songs the MusicPlayer knows.
     * A Song has a name, SONG_ID and file
     *
     * @return Song[]: Alias to songs
     */
    public Song[] getSongs() {
        return songs;
    }

    public Song getSong(int songId) {
        return songs[songId];
    }

    /**
     * Get the Song (as a LoadedSong) which is currently playing
     *
     * @return LoadedSong: currentSong
     */
    public LoadedSong getCurrentSong() {
        return currentSong;
    }

    public Integer[] getHistory() {
        Integer[] intArray = new Integer[history.size()];
        return history.toArray(intArray);
    }

    // history Functions

    private void setHistoryPointer(int newPos) {
        if (history.size() < newPos || -1 > newPos) return;

        historyPointer = newPos;

        HistoryPointerChangedEvent event = (HistoryPointerChangedEvent) getEvent(HistoryPointerChangedEvent.class);
        event.callListeners(newPos);
    }

    public int getHistoryPos() {
        return historyPointer;
    }

    public int getHistorySongId() {
        return history.get(historyPointer);
    }

    public boolean historyGoTo(int historyPos) {
        if (historyPos >= history.size() || historyPos < 0) {
            return false;
        }
        setHistoryPointer(historyPos);
        return true;
    }

    public void historyGoNewest() {
        setHistoryPointer(0);
    }

    public void historyBack(int count) {
        for (int i = 0; i < count; i++) {
            setHistoryPointer(Math.min(historyPointer + 1, history.size() - 1));
        }
    }

    public void historyNext(int count) {
        for (int i = 0; i < count; i++) {
            setHistoryPointer(Math.max(historyPointer - 1, -1));

            if (historyPointer == -1) {
                historyAdd(rng.nextInt(0, SONG_COUNT));
                setHistoryPointer(0);
            }
        }
    }

    public void historyAddAndJump(int songId) {
        historyAdd(songId);
        historyGoNewest();
    }

    public boolean historyAdd(int songId) {
        if (songId >= songs.length || songId < 0)
            return false;

        history.addFirst(songId);
        if (history.size() > HISTORY_MAX_SIZE) {
            history.removeLast();
        }

        NewHistoryEntryEvent event = (NewHistoryEntryEvent) getEvent(NewHistoryEntryEvent.class);
        event.callListeners(songs[history.getFirst()]);

        return true;
    }

    public int historyLoadSong() throws IOException {
        return loadSong(history.get(historyPointer));
    }

    public boolean startSong(int songID) {
        try {
            exitSong();
            if (loadSong(songID) != 0) {
                return false;
            }
            historyAddAndJump(songID);
            continueSong();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public boolean startRandomSong() {
        int songID = rng.nextInt(0, SONG_COUNT);
        return startSong(songID);
    }

    // Playback functions

    /**
     * loading a Song is required before playing it
     *
     * @param songId int: the songId of the song to load
     * @return int: -2 if the song can not be loaded, -1 if the songId is invalid,
     * 1 if the song is already loaded or 0 if the song is now loaded
     */
    private int loadSong(int songId) throws IOException {
        if (currentSong != null) return -1; // can not load Song
        if (songId > songs.length || songId < 0) return -1; // can not load Song
        if (currentSong != null && currentSong.getSongId() == songId)
            return 1; // will not load same Song two times

        LoadedSong song = new LoadedSong();
        boolean loadWorked = song.loadSong(songs[songId], confDir);
        if (!loadWorked) return -1; // can not load Song

        currentSong = song;

        SongLoadedEvent event = (SongLoadedEvent) getEvent(SongLoadedEvent.class);
        event.callListeners(song);

        return 0;
    }

    /**
     * Pauses the currently playing Song
     * If the currentSong is stopped noting happens.
     *
     * @return FALSE if there is no current song else TRUE
     */
    public boolean stopSong() {
        if (currentSong == null) return false; // no song to continue

        currentSong.stop();
        return true;
    }

    /**
     * continues playing after a Song got stopped from stopSong().
     * If the currentSong is not stopped noting happens.
     *
     * @return FALSE if there is no song to continue else TRUE
     */
    public boolean continueSong() {
        if (currentSong == null) return false; // no song to continue

        currentSong.start();
        return true;
    }

    /**
     * Sets the time position of the current Song to jumpTime
     *
     * @param jumpTime the time to jump to in microseconds
     * @throws NullPointerException if there is no currentSong
     */
    public void jumpTo(long jumpTime) throws NullPointerException {
        currentSong.setTime(jumpTime);
    }

    /**
     * Lets you jump forward in the Song. This works by adding the skipTime to the currentTime and then jump to the new time.
     *
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
     *
     * @param rewindTime time to rewind in microseconds
     */
    public void rewindTime(long rewindTime) {
        if (currentSong == null) return;

        currentSong.setTime(
                currentSong.getCurrentTime() - rewindTime
        );
    }

    /**
     * Stops the currentSong and then sets the currentSong to null.
     */
    public void exitSong() {
        if (currentSong == null) return;

        currentSong.exit();
        currentSong = null;
    }
}
