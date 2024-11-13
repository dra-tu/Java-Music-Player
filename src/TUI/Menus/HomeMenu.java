package TUI.Menus;

import MusicPlayer.MusicPlayer;

import TUI.Terminal.TerminalInput;
import TUI.Menus.Options.HomeOption;
import TUI.Terminal.TerminalHelper;
import TUI.Terminal.TerminalLock;
import TUI.Terminal.TerminalPosition;

import java.io.IOException;
import java.util.HashMap;

public class HomeMenu extends MenuBase {
    MenuManager menuMgr;
    SongMenu songMenu;

    HashMap<String, HomeOption> homeOptions = new HashMap<>();

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

        prompt = "Select option [i/q/m/.../? shows all options]: ";
        exitMsg = "Have a great day!";
        optionsMenu = """
                
                i: lIst
                p: Play
                m: Mix
                r: Reload
                l: cLear
                ?: help
                q: Quit
                
                """;

        homeOptions.put("i", HomeOption.LIST);
        homeOptions.put("p", HomeOption.PLAY);
        homeOptions.put("m", HomeOption.MIX);
        homeOptions.put("r", HomeOption.RELOAD);
        homeOptions.put("l", HomeOption.CLEAR);
        homeOptions.put("?", HomeOption.HELP);
        homeOptions.put("q", HomeOption.QUIT);
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

            switch (homeOptions.get(in)) {

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
                    musicPlayer.reloadDir();
                    terminalHelper.savePrintln("Done!");
                    break;

                // TUI Controls
                case HomeOption.QUIT: // Quit
                    quid();
                    return 0;
                case HomeOption.CLEAR: // cLear
                    clear();
                    break;
                case HomeOption.HELP: // show options
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
