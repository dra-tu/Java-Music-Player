package TUI.Menus;

import MusicPlayer.MusicPlayer;
import MusicPlayer.Types.TimeStamp;

import TUI.Terminal.TerminalInput;
import TUI.Menus.Options.SongOption;
import TUI.Terminal.TerminalHelper;
import TUI.Terminal.TerminalLock;
import TUI.Terminal.TerminalPosition;

import java.io.IOException;
import java.util.HashMap;

public class SongMenu extends MenuBase {
    HashMap<String, SongOption> songOptions = new HashMap<>();

    // set Menu specific Strings in here
    public SongMenu(
            TerminalPosition startPos,
            MusicPlayer musicPlayer,
            TerminalLock termLock,
            TerminalHelper terminalHelper,
            TerminalInput terminalInput
    ) {
        super(startPos, musicPlayer, termLock, terminalHelper, terminalInput);

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

    void quid() {
        terminalHelper.savePrintln(exitMsg);
        musicPlayer.exitSong();
        clear();
    }

    @Override
    int action() {
        String in;
        TimeStamp jumpTime;

        while (true) {

            try {
                in = terminalInput.getString(prompt);
            } catch (IOException | InterruptedException e) {
                quid();
                return 1;
            }
            terminalHelper.savePrintln(inputMsg + in);

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
                        jumpTime = terminalInput.getTimeStamp();
                    } catch (IOException | InterruptedException e) {
                        terminalHelper.savePrintln("an error has curd");
                        quid();
                        return 1;
                    }
                    musicPlayer.jumpTo(jumpTime);
                    break;
                case SongOption.SKIP: // sKips
                    try {
                        jumpTime = terminalInput.getTimeStamp();
                    } catch (IOException | InterruptedException e) {
                        terminalHelper.savePrintln("an error has curd");
                        quid();
                        return 1;
                    }
                    musicPlayer.skipTime(jumpTime);
                    break;
                case SongOption.REWIND: // Rewind
                    try {
                        jumpTime = terminalInput.getTimeStamp();
                    } catch (IOException | InterruptedException e) {
                        terminalHelper.savePrintln("an error has curd");
                        quid();
                        return 1;
                    }
                    musicPlayer.rewindTime(jumpTime);
                    break;

                // TUI Controls
                case SongOption.QUIT: // Quit
                    quid();
                    return 0;
                case SongOption.CLEAR: // cLear
                    clear();
                    break;
                case SongOption.HELP: // show options
                    terminalHelper.savePrintln(optionsMenu);
                    break;
                case null:
                default:
                    terminalHelper.savePrintln(unknownMsg);
                    break;
            }
        }
    }
}
