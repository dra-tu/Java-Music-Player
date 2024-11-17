package TUI.Menus;

import MusicPlayer.MusicPlayer;
import MusicPlayer.Types.Song;

import TUI.Menus.Options.HomeOption;

import TUI.TUI;
import TUI.Terminal.TerminalInput;
import TUI.Terminal.TerminalHelper;
import TUI.Terminal.TerminalLock;
import TUI.Terminal.TerminalPosition;

import java.io.IOException;

public class HomeMenu extends MenuBase {
    TUI tui;
    SongMenu songMenu;

    String songList;

    public HomeMenu(
            TerminalPosition startPos,
            MusicPlayer musicPlayer,
            TerminalLock termLock,
            TerminalHelper terminalHelper,
            TerminalInput terminalInput,
            TUI tui,
            SongMenu songMenu
    ) {
        super(startPos, musicPlayer, termLock, terminalHelper, terminalInput);

        this.tui = tui;
        this.songMenu = songMenu;

        songList = genSongList();

        prompt = "Select option [li/pl/mi/.../? shows all options]: ";
        exitMsg = "Have a great day!";
    }

    void quid() {
        terminalHelper.savePrintln(exitMsg);
    }

    private String  genSongList() {
        StringBuilder out = new StringBuilder();
        Song[] songs = musicPlayer.getSongs();

        for(Song song: songs) {
            out.append(" - (")
                    .append( String.format("%3d", song.getSongId())) // format of SONG_ID whit lIst in HomeMenu
                    .append(") ")
                    .append(song.getName())
                    .append("\n");
        }

        return out.toString();
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
                    terminalHelper.savePrint(songList);
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

                    if (tui.playtSong(songId)) {
                        clear();
                        songMenu.start();
                    }
                    break;
                case HomeOption.MIX:
                    tui.startMixPlay();
                    break;
                case HomeOption.RELOAD:
                    terminalHelper.savePrintln("Reloading Songs ...");
                    String out = musicPlayer.reloadDir() ? "Done!" : "can not load Songs";
                    songList = genSongList();
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
