package TUI.Menus;

import MusicPlayer.MusicPlayer;
import MusicPlayer.TimeStamp;
import TUI.*;

public class SongMenu extends Menu {
    // set Menu specific Strings in here
    public SongMenu(
            TerminalPosition startPos,
            MusicPlayer musicPlayer,
            TerminalLock termLock,
            Share share,
            InputFunc inputFunc
    ) {
        super(startPos, musicPlayer, termLock, share, inputFunc);

        prompt = "Select option [p/c/q/.../? shows all options]: ";
        unknownMsg = "Good By!";
        exitMsg = "Song done";
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

    @Override
    void setUp() {
        termLock.lockTerminal();
        TerminalControl.setCursorPos(startPos);
        termLock.unlockTerminal();
    }

    @Override
    void action() {
        String in;
        TimeStamp jumpTime;

        while (true) {
            in = inputFunc.getString(prompt);
            share.savePrintln(inputMsg + in);

            switch (songOptions.get(in)) {

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
                    return;
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
        }
    }
}
