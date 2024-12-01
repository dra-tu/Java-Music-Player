package tui.menus;

import musicPlayer.MusicPlayer;

import tui.TUI;
import tui.terminal.*;

import static tui.terminal.TerminalColor.GREEN;
import static tui.terminal.TerminalColor.RESET;

import java.io.IOException;

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

    void quid() {
        terminalHelper.savePrintln(exitMsg);
        musicPlayer.exitSong();
        clear();
    }

    @Override
    MenuExit action() {
        String in;
        while (true) {
            // get input
            try {
                in = terminalInput.getString(prompt);
            } catch (IOException | InterruptedException e) {
                terminalHelper.savePrintln("an error has curd");
                quid();
                return MenuExit.ERROR;
            }
            terminalHelper.savePrintln(inputMsg + in);

            // convert do option
            SongOption selectedOption = SongOption.getByKey(in);

            // execute Option
            MenuExit exit = selectedOption.action(this);
            if (exit != null)
                return exit;
        }
    }
}
