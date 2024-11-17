package TUI.Menus;

import MusicPlayer.MusicPlayer;
import TUI.TimeStamp;

import TUI.Terminal.TerminalInput;
import TUI.Menus.Options.SongOption;
import TUI.Terminal.TerminalHelper;
import TUI.Terminal.TerminalLock;
import TUI.Terminal.TerminalPosition;

import java.io.IOException;

public class SongMenu extends MenuBase {

    // set Menu specific Strings in here
    public SongMenu(
            TerminalPosition startPos,
            MusicPlayer musicPlayer,
            TerminalLock termLock,
            TerminalHelper terminalHelper,
            TerminalInput terminalInput
    ) {
        super(startPos, musicPlayer, termLock, terminalHelper, terminalInput);

        prompt = "Select option [pa/co/q/.../? shows all options]: ";
        exitMsg = "Song done";
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

            switch (SongOption.getByKey(in)) {

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
                    musicPlayer.jumpTo(jumpTime.getMicroseconds());
                    break;
                case SongOption.SKIP: // sKips
                    try {
                        jumpTime = terminalInput.getTimeStamp();
                    } catch (IOException | InterruptedException e) {
                        terminalHelper.savePrintln("an error has curd");
                        quid();
                        return 1;
                    }
                    musicPlayer.skipTime(jumpTime.getMicroseconds());
                    break;
                case SongOption.REWIND: // Rewind
                    try {
                        jumpTime = terminalInput.getTimeStamp();
                    } catch (IOException | InterruptedException e) {
                        terminalHelper.savePrintln("an error has curd");
                        quid();
                        return 1;
                    }
                    musicPlayer.rewindTime(jumpTime.getMicroseconds());
                    break;

                // TUI Controls
                case SongOption.QUIT: // Quit
                    quid();
                    return 0;
                case SongOption.CLEAR: // cLear
                    clear();
                    break;
                case SongOption.HELP: // show options
                    terminalHelper.savePrintln(SongOption.getHelpString());
                    break;
                case null:
                default:
                    terminalHelper.savePrintln(unknownMsg);
                    break;
            }
        }
    }
}
