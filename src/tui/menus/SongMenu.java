package tui.menus;

import musicPlayer.MusicPlayer;

import tui.menus.options.MixOption;
import tui.menus.options.SongOption;

import tui.terminal.TerminalInput;
import tui.terminal.TerminalHelper;
import tui.terminal.TerminalLock;
import tui.terminal.TerminalPosition;

import java.io.IOException;

public class SongMenu extends MenuBase {

    private boolean mixPlay;

    // set Menu specific Strings in here
    public SongMenu(
            TerminalPosition startPos,
            MusicPlayer musicPlayer,
            TerminalLock termLock,
            TerminalHelper terminalHelper,
            TerminalInput terminalInput
    ) {
        super(startPos, musicPlayer, termLock, terminalHelper, terminalInput);

        mixPlay = false;

        prompt = "Select option [p/o/q/.../? shows all options]: ";
        exitMsg = "Song done";
    }

    public void setMixPlay(boolean mixPlay) {
        this.mixPlay = mixPlay;
    }

    void quid() {
        terminalHelper.savePrintln(exitMsg);
        musicPlayer.exitSong();
        clear();
    }

    @Override
    int action() {
        String in;
        long jumpTime;

        while (true) {

            try {
                in = terminalInput.getString(prompt);
            } catch (IOException | InterruptedException e) {
                quid();
                return -1;
            }
            terminalHelper.savePrintln(inputMsg + in);

            switch (SongOption.getByKey(in)) {

                case SHOW_HISTORY:
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
                        return -1;
                    }
                    musicPlayer.jumpTo(jumpTime);
                    break;
                case SKIP: // sKips
                    try {
                        jumpTime = terminalInput.getTimeStamp();
                    } catch (IOException | InterruptedException e) {
                        terminalHelper.savePrintln("an error has curd");
                        quid();
                        return -1;
                    }
                    musicPlayer.skipTime(jumpTime);
                    break;
                case REWIND: // Rewind
                    try {
                        jumpTime = terminalInput.getTimeStamp();
                    } catch (IOException | InterruptedException e) {
                        terminalHelper.savePrintln("an error has curd");
                        quid();
                        return -1;
                    }
                    musicPlayer.rewindTime(jumpTime);
                    break;

                // TUI Controls
                case QUIT: // Quit
                    quid();
                    return 0;
                case CLEAR: // cLear
                    clear();
                    break;
                case HELP: // show options
                    terminalHelper.savePrintln(
                            (mixPlay ? MixOption.getHelpString() : "") +
                                    SongOption.getHelpString()
                    );
                    break;
                case null:
                default:
                    if (mixPlay) {
                        int sw = switch (MixOption.getByKey(in)) {
                            case NEXT -> 2;
                            case PREVIOUS -> 1;
                            case null -> 0;
                        };
                        if (sw != 0) {
                            quid();
                            return sw;
                        }
                    }
                    terminalHelper.savePrintln(unknownMsg);
                    break;
            }
        }
    }
}
