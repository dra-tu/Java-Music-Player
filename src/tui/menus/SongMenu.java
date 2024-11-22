package tui.menus;

import musicPlayer.MusicPlayer;

import tui.menus.options.HomeOption;
import tui.menus.options.MixOption;
import tui.terminal.TerminalInput;
import tui.menus.options.SongOption;
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

                case SongOption.SHOW_HISTORY:
                    Integer[] history = musicPlayer.getHistory();
                    String songName;
                    for(int i = 0; i < history.length; i++) {
                        songName = musicPlayer.getSong(history[i]).getName();
                        System.out.println(i + ": (" + history[i] + ") " + songName);
                    }
                    break;

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
                        return -1;
                    }
                    musicPlayer.jumpTo(jumpTime);
                    break;
                case SongOption.SKIP: // sKips
                    try {
                        jumpTime = terminalInput.getTimeStamp();
                    } catch (IOException | InterruptedException e) {
                        terminalHelper.savePrintln("an error has curd");
                        quid();
                        return -1;
                    }
                    musicPlayer.skipTime(jumpTime);
                    break;
                case SongOption.REWIND: // Rewind
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
                case SongOption.QUIT: // Quit
                    quid();
                    return 0;
                case SongOption.CLEAR: // cLear
                    clear();
                    break;
                case SongOption.HELP: // show options
                    terminalHelper.savePrintln(
                            (mixPlay ? MixOption.getHelpString() : "" ) +
                        SongOption.getHelpString()
                    );
                    break;
                case null:
                default:
                    if (mixPlay) {
                        int sw = switch (MixOption.getByKey(in)) {
                            case MixOption.NEXT -> 2;
                            case MixOption.PREVIOUS -> 1;
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
