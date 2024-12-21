package tui.menus;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;

import static tui.terminal.TerminalColor.RED;
import static tui.terminal.TerminalColor.RESET;

public enum HomeOption {
    // they are used but not directly
    PLAY("p", "Play") {
        @Override
        MenuExit action(HomeMenu homeMenu) {
            homeMenu.tui.mixPlay(true);
            return null;
        }
    },
    PLAY_FROM_SONG_ID("ps", "Start play whit specific song") {
        @Override
        MenuExit action(HomeMenu homeMenu) {
            int songId;
            try {
                songId = homeMenu.terminalInput.getInt("Song id: ");
            } catch (IOException | InterruptedException e) {
                homeMenu.terminalHelper.savePrintln("an error has curd");
                homeMenu.quid();
                return MenuExit.ERROR;
            } catch (InputMismatchException e) {
                homeMenu.terminalHelper.savePrintln("No Song whit this SongID");
                return null;
            }

            if (!homeMenu.tui.mixPlayFromId(songId)) {
                homeMenu.terminalHelper.savePrintln("No Song whit SongID: " + songId);
            }
            return null;
        }
    },
    PLAY_FROM_HISTORY("ph", "Start Play from point in history") {
        @Override
        MenuExit action(HomeMenu homeMenu) {
            int historyPos;
            try {
                historyPos = homeMenu.terminalInput.getInt("History Number: ");
            } catch (IOException | InterruptedException e) {
                homeMenu.terminalHelper.savePrintln("an error has curd");
                homeMenu.quid();
                return MenuExit.ERROR;
            } catch (InputMismatchException e) {
                homeMenu.terminalHelper.savePrintln("No Song at this history point");
                return null;
            }

            if (!homeMenu.tui.mixPlayFromHistory(historyPos)) {
                homeMenu.terminalHelper.savePrintln("No History point: " + historyPos);
            }
            return null;
        }
    },

    LIST_SONGS("ls", "List song") {
        @Override
        MenuExit action(HomeMenu homeMenu) {
            homeMenu.terminalHelper.savePrint(homeMenu.songList);
            return null;
        }
    },
    LIST_HISTORY("lh", "list history") {
        @Override
        MenuExit action(HomeMenu homeMenu) {
            homeMenu.printHistory();
            return null;
        }
    },
    LIST_ERRORS("le", "list errors") {
        @Override
        MenuExit action(HomeMenu homeMenu) {
            homeMenu.terminalHelper.savePrint(homeMenu.tui.getErrorLog());
            return null;
        }
    },

    VOLUME_DEFAULT("vd", "Set the default Volume [ Volume for Songs where the volume isn't set ]") {
        @Override
        MenuExit action(HomeMenu homeMenu) {
            int percent;
            try {
                percent = homeMenu.terminalInput.getInt("% ");

                if (!homeMenu.musicPlayer.setDefaultVolumePercent(percent)) {
                    homeMenu.terminalHelper.savePrintln("Pleas insert a value between 0 and 100");
                }

                return null;
            } catch (IOException | InterruptedException e) {
                homeMenu.terminalHelper.savePrintln("an error has curd");
                homeMenu.tui.addToErrorLog("IOException @ SongOption @ VolumeDefault");
                homeMenu.quid();
                return MenuExit.ERROR;
            } catch (InputMismatchException e) {
                homeMenu.terminalHelper.savePrintln("This is not a Number");
                return null;
            }
        }
    },

    CHANGE_DIR("cd", "Change directory") {
        @Override
        MenuExit action(HomeMenu homeMenu) {
            String newDir;
            String workingDir = System.getProperty("user.dir")+"/";
            try {
                newDir = homeMenu.terminalInput.getString("Directory? " + workingDir);
            } catch (IOException | InterruptedException e) {
                homeMenu.terminalHelper.savePrintln(RED + "ERROR" + RESET);
                homeMenu.tui.addToErrorLog("getting input didn't work");
                return null;
            }
            if (homeMenu.tui.setDir(workingDir + newDir))
                homeMenu.genSongList();
            return null;
        }
    },
    RELOAD_DIR("rd", "Reload directory") {
        @Override
        MenuExit action(HomeMenu homeMenu){
            try {
                homeMenu.terminalHelper.savePrintln("Reloading Songs ...");
                String out = homeMenu.musicPlayer.reloadDir() ? "Done!" : "can not load Songs";
                homeMenu.genSongList();
                homeMenu.terminalHelper.savePrintln(out);
            } catch (IOException e) {
                homeMenu.terminalHelper.savePrintln(RED + "cant reload Directory" + RESET);
                homeMenu.tui.addToErrorLog("cant reload Directory");
                return MenuExit.ERROR;
            }
            return null;
        }
    },

    CLEAR("c", "Clear") {
        @Override
        MenuExit action(HomeMenu homeMenu) {
            homeMenu.clear();
            return null;
        }
    },
    HELP("?", "Help") {
        @Override
        MenuExit action(HomeMenu homeMenu) {
            homeMenu.terminalHelper.savePrintln(HELP_STRING);
            return null;
        }
    },
    QUIT("q", "Quit") {
        @Override
        MenuExit action(HomeMenu homeMenu) {
            homeMenu.quid();
            return MenuExit.USER_EXIT;
        }
    },

    NOT_AN_OPTION("", "") {
        @Override
        MenuExit action(HomeMenu homeMenu) {
            homeMenu.terminalHelper.savePrintln(MenuBase.unknownMsg);
            return null;
        }
    };

    // this part is the same in all Option Enums
    // EXCEPTIONS: Name of constructor, Type in HasMap, in static for loop, return Type of getByKey
    final String KEY;
    final String DESCRIPTION;

    abstract MenuExit action(HomeMenu homeMenu);

    private static final HashMap<String, HomeOption> KEY_MAP;
    static final String HELP_STRING;

    HomeOption(String KEY, String DESCRIPTION) {
        this.KEY = KEY;
        this.DESCRIPTION = DESCRIPTION;
    }

    public static HomeOption getByKey(String key) {
        HomeOption option = KEY_MAP.get(key);
        return option == null
                ? HomeOption.NOT_AN_OPTION
                : option;
    }

    static {
        OptionHelper.ReturnValue<HomeOption> b = OptionHelper.genHelpStringAndKeyMap(HomeOption.class);
        HELP_STRING = b.HELP_STRING;
        KEY_MAP = b.KEY_MAP;
    }
}