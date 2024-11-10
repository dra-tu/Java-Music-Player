package TUI.Menus;

import MusicPlayer.MusicPlayer;
import MusicPlayer.TimeStamp;
import TUI.*;

public class SongMenu extends Menu {
    // set Menu specific Strings in here
    public SongMenu(Share share, MusicPlayer musicPlayer, TerminalLock termLock, TerminalPosition startPos) {
        super(share, musicPlayer, termLock, startPos);
        prompt = "Select option [p/c/q/.../? shows all options]: ";
        unknownMsg = "Good By!";
        exitMsg = "BY";
        inputMsg = "=> ";
        optionsMenu = """
                
                p: Pause
                c: Continue
                j: Jump
                k: sKips
                r: Rewind
                l: cLear
                ?: help
                q: Quit
                
                """;

        songOptions.put("p", SongOption.PAUSE);
        songOptions.put("c", SongOption.CONTINUE);
        songOptions.put("j", SongOption.JUMP);
        songOptions.put("k", SongOption.SKIP);
        songOptions.put("r", SongOption.REWIND);
        songOptions.put("l", SongOption.CLEAR);
        songOptions.put("q", SongOption.QUIT);
        songOptions.put("?", SongOption.HELP);
    }

    public boolean action(String input) {
        TimeStamp jumpTime;
        switch (songOptions.get(input)) {

            // MusicPlayer Controls
            case SongOption.PAUSE: // Pause
                musicPlayer.stopSong();
                break;
            case SongOption.CONTINUE: // Continue
                musicPlayer.continueSong();
                break;
            case SongOption.JUMP: // Jump
                jumpTime = inputFunc.getTimeStamp();
                musicPlayer.jumpTo(jumpTime);
                break;
            case SongOption.SKIP: // sKips
                jumpTime = inputFunc.getTimeStamp();
                musicPlayer.skipTime(jumpTime);
                break;
            case SongOption.REWIND: // Rewind
                jumpTime = inputFunc.getTimeStamp();
                musicPlayer.rewindTime(jumpTime);
                break;

            // TUI Controls
            case SongOption.QUIT: // Quit
                share.savePrintln(exitMsg);
                return true;
            case SongOption.CLEAR: // cLear
                clear();
                break;
            case SongOption.HELP: // show options
                share.savePrintln(optionsMenu);
                break;
            case null:
            default:
                share.savePrintln(unknownMsg);
                break;
        }
        return false; // unreachable
    }
}
