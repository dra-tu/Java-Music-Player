package TUI.Menus;

import MusicPlayer.MusicPlayer;

import TUI.Terminal.TerminalInput;
import TUI.Menus.Options.HomeOption;
import TUI.Terminal.TerminalHelper;
import TUI.Terminal.TerminalLock;
import TUI.Terminal.TerminalPosition;

import java.io.IOException;

public class HomeMenu extends MenuBase {
    MenuManager menuMgr;
    SongMenu songMenu;

    public HomeMenu(
            TerminalPosition startPos,
            MusicPlayer musicPlayer,
            TerminalLock termLock,
            TerminalHelper terminalHelper,
            TerminalInput terminalInput,
            MenuManager mgr,
            SongMenu songMenu
    ) {
        super(startPos, musicPlayer, termLock, terminalHelper, terminalInput);

        this.menuMgr = mgr;
        this.songMenu = songMenu;

        prompt = "Select option [li/pl/mi/.../? shows all options]: ";
        exitMsg = "Have a great day!";
    }

    void quid() {
        terminalHelper.savePrintln(exitMsg);
    }

    @Override
    int action() {
        String in;
        while (true) {
            try {
                in = terminalInput.getString(prompt);
            } catch (IOException | InterruptedException e) {
                quid();
                return 1;
            }
            terminalHelper.savePrintln(inputMsg + in);

            switch (HomeOption.getByKey(in)) {

                // MusicPlayer Controls
                case HomeOption.LIST:
                    terminalHelper.savePrint(musicPlayer.getSongList());
                    break;
                case HomeOption.PLAY:
                    int songId;
                    try {
                         songId = terminalInput.getInt("Song id: ");
                    } catch (IOException | InterruptedException e) {
                        terminalHelper.savePrintln("an error has curd");
                        quid();
                        return 1;
                    }
                    clear();

                    if (menuMgr.startSong(songId)) {
                        clear();
                        songMenu.start();
                    }
                    break;
                case HomeOption.MIX:
                    menuMgr.startMixPlay();
                    break;
                case HomeOption.RELOAD:
                    terminalHelper.savePrintln("Reloading Songs ...");
                    String out = musicPlayer.reloadDir() ? "Done!" : "can not load Songs";
                    terminalHelper.savePrintln(out);
                    break;

                // TUI Controls
                case HomeOption.QUIT: // Quit
                    quid();
                    return 0;
                case HomeOption.CLEAR: // cLear
                    clear();
                    break;
                case HomeOption.HELP: // show options
                    terminalHelper.savePrintln(HomeOption.getHelpString());
                    break;
                case null:
                default:
                    terminalHelper.savePrintln(unknownMsg);
                    break;
            }
        }
    }
}
