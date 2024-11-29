package tui.menus;

import musicPlayer.MusicPlayer;

import tui.menus.options.SongOption;
import tui.terminal.*;

import static tui.terminal.TerminalColor.GREEN;
import static tui.terminal.TerminalColor.RESET;

import java.io.IOException;
import java.util.InputMismatchException;

public class SongMenu extends MenuBase {
    static {
        prompt = "Select option [" +
                GREEN + "p" + RESET +
                "/" + GREEN + "o" + RESET +
                "/" + GREEN + "c" + RESET +
                "/..." +
                "/" + GREEN + "?" + RESET +
                " shows all options]: ";
        exitMsg = "Song done";
    }

    // set Menu specific Strings in here
    public SongMenu(
            TerminalPosition startPos,
            MusicPlayer musicPlayer,
            TerminalLock termLock,
            TerminalHelper terminalHelper,
            TerminalInput terminalInput
    ) {
        super(startPos, musicPlayer, termLock, terminalHelper, terminalInput);
    }

    void quid() {
        terminalHelper.savePrintln(exitMsg);
        musicPlayer.exitSong();
        clear();
    }

    @Override
    MenuExit action() {
        String in;
        long jumpTime;

        while (true) {

            try {
                in = terminalInput.getString(prompt);
            } catch (IOException | InterruptedException e) {
                terminalHelper.savePrintln("an error has curd");
                quid();
                return MenuExit.ERROR;
            }
            terminalHelper.savePrintln(inputMsg + in);

            switch (SongOption.getByKey(in)) {

                case LIST_HISTORY:
                    printHistory();
                    break;

                // MusicPlayer Controls
                case PAUSE: // Pause
                    musicPlayer.stopSong();
                    break;
                case CONTINUE: // Continue
                    musicPlayer.continueSong();
                    break;
                case JUMP: // Jump
                    try {
                        jumpTime = terminalInput.getTimeStamp();
                    } catch (IOException | InterruptedException e) {
                        terminalHelper.savePrintln("an error has curd");
                        quid();
                        return MenuExit.ERROR;
                    } catch (InputMismatchException e) {
                        terminalHelper.savePrintln("This is not a Time");
                        break;
                    }
                    musicPlayer.jumpTo(jumpTime);
                    break;
                case SKIP: // sKips
                    try {
                        jumpTime = terminalInput.getTimeStamp();
                    } catch (IOException | InterruptedException e) {
                        terminalHelper.savePrintln("an error has curd");
                        quid();
                        return MenuExit.ERROR;
                    } catch (InputMismatchException e) {
                        terminalHelper.savePrintln("This is not a Time");
                        break;
                    }
                    musicPlayer.skipTime(jumpTime);
                    break;
                case REWIND: // Rewind
                    try {
                        jumpTime = terminalInput.getTimeStamp();
                    } catch (IOException | InterruptedException e) {
                        terminalHelper.savePrintln("an error has curd");
                        quid();
                        return MenuExit.ERROR;
                    } catch (InputMismatchException e) {
                        terminalHelper.savePrintln("This is not a Time");
                        break;
                    }
                    musicPlayer.rewindTime(jumpTime);
                    break;

                // TUI Controls
                case QUIT: // Quit
                    quid();
                    return MenuExit.USER_EXIT;
                case CLEAR: // cLear
                    clear();
                    break;
                case HELP: // show options
                    terminalHelper.savePrintln(SongOption.getHelpString());
                    break;

                case NEXT:
                    quid();
                    return MenuExit.SONG_Next;
                case BACK:
                    quid();
                    return MenuExit.SONG_BACK;

                case null:
                default:
                    terminalHelper.savePrintln(unknownMsg);
                    break;
            }
        }
    }
}
