package TUI;

import MusicPlayer.MusicPlayer;
import MusicPlayer.TimeStamp;

import java.util.HashMap;

public class TerminalInput implements Runnable {
    InputFunc inputFunc;
    TerminalPosition startPos;
    TerminalLock termLock;
    Share share;

    HashMap<String, SongOption> songOptions;

    MusicPlayer musicPlayer;

    // output Strings
    final String prompt = "Select option [p/c/q/.../? shows all options]: ";
    final String unknownMsg = "unknown Option!";
    final String exitMsg = "Good By!";
    final String inputMsg = "=> ";
    final String optionsMenu = """
                
                p: Pause
                c: Continue
                j: Jump
                k: sKips
                r: Rewind
                l: cLear
                q: Quit
                
                """;

    public TerminalInput(TerminalPosition startPos, MusicPlayer musicPlayer, TerminalLock termLock) {
        this.startPos = startPos;
        this.musicPlayer = musicPlayer;
        this.termLock = termLock;
        this.inputFunc = new InputFunc(termLock);
        this.share = new Share(termLock);

        songOptions = new HashMap<String, SongOption>();
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
    public void run() {
        termLock.lockTerminal();
        TerminalControl.setCursorPos(startPos);
        termLock.unlockTerminal();
        while (true) {

            String in = inputFunc.getString(prompt);
            share.savePrintln(inputMsg + in);

            TimeStamp jumpTime;
            switch (songOptions.get(in)) {

                // MusicPlayer Controls
                case  SongOption.PAUSE: // Pause
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
                    termLock.lockTerminal();
                    TerminalControl.clear();
                    TerminalControl.setCursorPos(startPos);
                    termLock.unlockTerminal();
                    break;
                case SongOption.HELP: // show options
                    showOptions();
                    break;
                case null:
                default:
                    share.savePrintln(unknownMsg);
                    break;
            }
        }
    }

    private void showOptions() {
        share.savePrintln(optionsMenu);
    }
}
