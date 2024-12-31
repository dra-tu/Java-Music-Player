package tui.menus.home;

import musicPlayer.MusicPlayer;

import tui.TUI;
import tui.menus.MenuBase;
import tui.menus.MenuExit;
import tui.menus.song.SongMenu;
import tui.terminal.*;

import java.io.IOException;

import static tui.terminal.TerminalColor.*;

public class HomeMenu extends MenuBase {
    SongMenu songMenu;

    static {
        prompt = "Select option [" +
                GREEN + "p" + RESET +
                "/" + GREEN + "l" + RESET +
                "/" + GREEN + "c" + RESET +
                "/..." +
                "/" + GREEN + "?" + RESET +
                " shows all options]: ";
        exitMsg = "Have a great day!";
    }

    public HomeMenu(
            TerminalPosition startPos,
            MusicPlayer musicPlayer,
            TerminalLock termLock,
            TerminalHelper terminalHelper,
            TerminalInput terminalInput,
            TUI tui,
            SongMenu songMenu
    ) {
        super(tui, startPos, musicPlayer, termLock, terminalHelper, terminalInput);

        this.songMenu = songMenu;
    }

    protected void quid() {
        terminalHelper.savePrintln(exitMsg);
    }

    @Override
    protected MenuExit action() {
        String in;
        while (true) {
            // get input
            try {
                in = terminalInput.getString(prompt);
            } catch (IOException | InterruptedException e) {
                quid();
                return MenuExit.ERROR;
            }
            terminalHelper.savePrintln(inputMsg + in);

            // convert do option
            HomeOption selectedOption = HomeOption.getByKey(in);

            // execute Option
            MenuExit exit = selectedOption.action(this);
            if (exit != null) {
                quid();
                return exit;
            }
        }
    }
}
