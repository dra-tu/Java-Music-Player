package tui.menus;

import musicPlayer.MusicPlayer;
import musicPlayer.types.Song;

import tui.menus.options.HomeOption;

import tui.TUI;
import tui.terminal.*;

import java.io.IOException;
import java.util.InputMismatchException;

public class HomeMenu extends MenuBase {
    TUI tui;
    SongMenu songMenu;
    String songList;

    static final String prompt =
            "Select option [" +
                    TerminalColor.GREEN + "p" + TerminalColor.RESET +
                    "/"+TerminalColor.GREEN+"l"+TerminalColor.RESET+
                    "/"+TerminalColor.GREEN+"c"+TerminalColor.RESET+
                    "/..."+
                    "/"+TerminalColor.GREEN+"?"+TerminalColor.RESET+
                    " shows all options]: ";

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

        exitMsg = "Have a great day!";
    }

    void quid() {
        terminalHelper.savePrintln(exitMsg);
    }

    private String genSongList() {
        StringBuilder out = new StringBuilder();
        Song[] songs = musicPlayer.getSongs();

        for (Song song : songs) {
            out.append(" - (")
                    .append(String.format("%3d", song.SONG_ID)) // format of SONG_ID whit lIst in HomeMenu
                    .append(") ")
                    .append(song.getName())
                    .append("\n");
        }

        return out.toString();
    }

    @Override
    MenuExit action() {
        String in;
        while (true) {
            try {
                in = terminalInput.getString(prompt);
            } catch (IOException | InterruptedException _) {
                quid();
                return MenuExit.ERROR;
            }
            terminalHelper.savePrintln(inputMsg + in);


            switch (HomeOption.getByKey(in)) {

                // MusicPlayer Controls
                case LIST:
                    terminalHelper.savePrint(songList);
                    break;
                case LIST_HISTORY:
                    printHistory();
                    break;

                case PLAY_FROM_SONG_ID:
                    int songId;
                    try {
                        songId = terminalInput.getInt("Song id: ");
                    } catch (IOException | InterruptedException _) {
                        terminalHelper.savePrintln("an error has curd");
                        quid();
                        return MenuExit.ERROR;
                    } catch (InputMismatchException _) {
                        terminalHelper.savePrintln("No Song whit this SongID");
                        break;
                    }

                    if(!tui.mixPlayFromId(songId)) {
                        terminalHelper.savePrintln("No Song whit SongID: " + songId);
                    }
                    break;
                case PLAY_FROM_HISTORY:
                    int historyPos;
                    try {
                        historyPos = terminalInput.getInt("History Number: ");
                    } catch (IOException | InterruptedException _) {
                        terminalHelper.savePrintln("an error has curd");
                        quid();
                        return MenuExit.ERROR;
                    } catch (InputMismatchException _) {
                        terminalHelper.savePrintln("No Song at this history point");
                        break;
                    }

                    if(!tui.mixPlayFromHistory(historyPos)) {
                        terminalHelper.savePrintln("No History point: " + historyPos);
                    }
                    break;
                case PLAY:
                    tui.mixPlay(true);
                    break;

                case LIST_ERRORS:
                    terminalHelper.savePrintln(tui.getErrorLog());
                    break;

                case RELOAD:
                    terminalHelper.savePrintln("Reloading Songs ...");
                    String out = musicPlayer.reloadDir() ? "Done!" : "can not load Songs";
                    songList = genSongList();
                    terminalHelper.savePrintln(out);
                    break;

                // TUI Controls
                case QUIT: // Quit
                    quid();
                    return MenuExit.USER_EXIT;
                case CLEAR: // cLear
                    clear();
                    break;
                case HELP: // show options
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
