package tui.menus.song;

import musicPlayer.MusicPlayer;

import tui.TUI;
import tui.menus.MenuBase;
import tui.menus.MenuExit;
import tui.terminal.*;

import java.io.IOException;

import static tui.terminal.TerminalColor.*;

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
            TUI tui,
            TerminalPosition startPos,
            MusicPlayer musicPlayer,
            TerminalLock termLock,
            TerminalHelper terminalHelper,
            TerminalInput terminalInput
    ) {
        super(tui, startPos, musicPlayer, termLock, terminalHelper, terminalInput);
    }

    protected void quid() {
        terminalHelper.savePrintln(exitMsg);
        musicPlayer.exitSong();
        clear();
    }

    @Override
    protected MenuExit action() {
        String in;
        while (true) {
            // get input
            try {
                in = terminalInput.getString(prompt);
            } catch (InterruptedException e) {
                // interrupt to start next son
                quid();
                return MenuExit.SONG_Next;
            } catch (IOException e) {
                tui.addToErrorLog("an IO error has curd @ SongMenu.action() @ terminalInput.getString: " + e);
                terminalHelper.savePrintln(RED + "an IO error has curd" + RESET);
                quid();
                return MenuExit.ERROR;
            }
            terminalHelper.savePrintln(inputMsg + in);

            // convert do option
            SongOption selectedOption = SongOption.getByKey(in);

            // execute Option
            MenuExit exit;
            try {
                exit = selectedOption.action(this);
            } catch (IOException e) {
                tui.addToErrorLog("an IO error has curd @ SongMenu.action() @ SongOption." + selectedOption + ".action: " + e);
                terminalHelper.savePrintln(RED + "an IO error has curd" + RESET);
                quid();
                return MenuExit.ERROR;
            }
            if (exit != null) {
                quid();
                return exit;
            }
        }
    }
}
