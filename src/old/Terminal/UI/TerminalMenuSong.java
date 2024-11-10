package old.Terminal.UI;

import static old.Share.MyPrint.println;
import static old.Terminal.UI.TerminalShare.getInput;

import old.MusicPlayer.MusicPlayer;
import old.MusicPlayer.MusicPlayerControls;
import old.Types.Song;

public class TerminalMenuSong implements Runnable {
    boolean mixed;
    MusicPlayerControls playerControls;
    MusicPlayer musicPlayer;

    // SongMenu Options
    final static char SONG_HELP = 'h';
    final static char SONG_STOP = 's';
    final static char SONG_CONTINUE = 'c';
    final static char SONG_JUMP = 'j';
    final static char SONG_SKIP_SEC = 'k';
    final static char SONG_REWIND_SEC = 'r';
    final static char SONG_QUIT = 'q';

    // Mixed Playback Options(these are displayed with the song menu)
    final static char MIXED_GO_SONG_FORWARD = 'f';
    final static char MIXED_GO_SONG_BACK = 'b';

    // constructor
    public TerminalMenuSong(MusicPlayer musicPlayer, boolean mixed) {
        this.mixed = mixed;
        this.playerControls = musicPlayer.getControls();
        this.musicPlayer = musicPlayer;
    }

    @Override
    public void run() {
        printSongMenuOptions();
        songControl(mixed);
    }

    private void printSongMenuOptions() {
        // TODO: Display total time of songControl and current time(-pos)
        Song curentSong = musicPlayer.getCurrentSong();
        int currentSongNumber = curentSong.getNumber();
        String currentSongName = curentSong.getName();

        println("--- Playing: (" + currentSongNumber + ") " +  currentSongName + " ---");

        println("h: Display this menu");
        println("s: Stop");
        println("c: Continue");
        println("j-<Min>-<Sec>: Jump to <Min>:<Sec>");
        println("k-<Min>-<Sec>: Skip <Min> Minutes and <Sec> Seconds");
        println("r-<Min>-<Sec>: Rewind <Min> Minutes and <Sec> Seconds");
        println("q: Quit");
    }

    private void songControl(boolean useMixed) {
        while (true) {
            String[] option = getInput();
            switch (option[0].charAt(0)) {
                case SONG_HELP:
                    printSongMenuOptions();
                    break;
                case SONG_STOP:
                    playerControls.stopSong();
                    break;
                case SONG_CONTINUE:
                    playerControls.continueSong();
                    break;
                case SONG_QUIT:
                    // TODO: had a case where I was not able to exit song replay. I don't know why but it needs fixing
                    playerControls.quitSong();
                    return;
                case SONG_JUMP:
                    playerControls.jumpSong(Long.parseLong(option[1]), Long.parseLong(option[2]));
                    break;
                case SONG_SKIP_SEC:
                    playerControls.skipSongSec(Long.parseLong(option[1]), Long.parseLong(option[2]));
                    break;
                case SONG_REWIND_SEC:
                    playerControls.rewindSongSec(Long.parseLong(option[1]), Long.parseLong(option[2]));
                    break;

                // Mixed Playback Controls
                case MIXED_GO_SONG_FORWARD:
                    if (useMixed) {

                        break;
                    }
                case MIXED_GO_SONG_BACK:
                    if (useMixed) {

                        break;
                    }

                default:
                    println("=> unknown option");
                    break;
            }
        }
    }

}
