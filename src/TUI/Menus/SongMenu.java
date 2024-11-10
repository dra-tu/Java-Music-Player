package TUI.Menus;

import MusicPlayer.MusicPlayer;
import MusicPlayer.TimeStamp;
import TUI.*;

import java.io.IOException;
import java.util.HashMap;

public class SongMenu extends Menu {
    HashMap<String, SongOption> songOptions = new HashMap<>();

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
        exitMsg = "Song done";
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

    private void quid() {
        share.savePrintln(exitMsg);
        musicPlayer.exitSong();
        clear();
    }

    @Override
    void action() {
        String in;
        TimeStamp jumpTime;

        while (true) {

            try {
                in = inputFunc.getString(prompt);
            } catch (IOException | InterruptedException e) {
                quid();
                return;
            }
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
                    try {
                        jumpTime = inputFunc.getTimeStamp();
                    } catch (IOException | InterruptedException e) {
                        share.savePrintln("an error has curd");
                        quid();
                        return;
                    }
                    musicPlayer.jumpTo(jumpTime);
                    break;
                case SongOption.SKIP: // sKips
                    try {
                        jumpTime = inputFunc.getTimeStamp();
                    } catch (IOException | InterruptedException e) {
                        share.savePrintln("an error has curd");
                        quid();
                        return;
                    }
                    musicPlayer.skipTime(jumpTime);
                    break;
                case SongOption.REWIND: // Rewind
                    try {
                        jumpTime = inputFunc.getTimeStamp();
                    } catch (IOException | InterruptedException e) {
                        share.savePrintln("an error has curd");
                        quid();
                        return;
                    }
                    musicPlayer.rewindTime(jumpTime);
                    break;

                // TUI Controls
                case SongOption.QUIT: // Quit
                    quid();
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
