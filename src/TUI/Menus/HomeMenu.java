package TUI.Menus;

import MusicPlayer.MusicPlayer;
import TUI.InputFunc;
import TUI.Share;
import TUI.TerminalLock;
import TUI.TerminalPosition;

import java.io.IOException;
import java.util.HashMap;

public class HomeMenu extends Menu {
    MenuManager menuMgr;
    SongMenu songMenu;

    HashMap<String, HomeOption> homeOptions = new HashMap<>();

    public HomeMenu(
            TerminalPosition startPos,
            MusicPlayer musicPlayer,
            TerminalLock termLock,
            Share share,
            InputFunc inputFunc,
            MenuManager mgr,
            SongMenu songMenu
    ) {
        super(startPos, musicPlayer, termLock, share, inputFunc);

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

    private void quid() {
        share.savePrintln(exitMsg);
    }

    @Override
    void action() {
        String in;
        while (true) {
            try {
                in = inputFunc.getString(prompt);
            } catch (IOException | InterruptedException e) {
                quid();
                return;
            }
            share.savePrintln(inputMsg + in);

            switch (homeOptions.get(in)) {

                // MusicPlayer Controls
                case HomeOption.LIST:
                    share.savePrint(musicPlayer.getSongList());
                    break;
                case HomeOption.PLAY:
                    int songId;
                    try {
                         songId = inputFunc.getInt("Song id: ");
                    } catch (IOException | InterruptedException e) {
                        share.savePrintln("an error has curd");
                        quid();
                        return;
                    }
                    clear();

                    if (menuMgr.startSong(songId)) {
                        clear();
                        songMenu.start();
                    } else share.savePrintln("cannot load Song");
                    break;
                case HomeOption.MIX:
                    break;
                case HomeOption.RELOAD:
                    share.savePrintln("Reloading Songs ...");
                    musicPlayer.reloadDir();
                    share.savePrintln("Done!");
                    break;

                // TUI Controls
                case HomeOption.QUIT: // Quit
                    quid();
                    return;
                case HomeOption.CLEAR: // cLear
                    clear();
                    break;
                case HomeOption.HELP: // show options
                    share.savePrintln(optionsMenu);
                    break;
                case null:
                default:
                    share.savePrintln(unknownMsg);
                    break;
            }
        }
    }
}
