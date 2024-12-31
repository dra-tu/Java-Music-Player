package tui.menus.home;

import tui.menus.MenuBase;
import tui.menus.MenuExit;
import tui.menus.OptionHelper;
import tui.menus.ReturnValue;

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
            homeMenu.getTui().mixPlay(true);
            return null;
        }
    },
    PLAY_FROM_SONG_ID("ps", "Start play whit specific song") {
        @Override
        MenuExit action(HomeMenu homeMenu) {
            int songId;
            try {
                songId = homeMenu.getTerminalInput().getInt("Song id: ");
            } catch (IOException | InterruptedException e) {
                homeMenu.getTerminalHelper().savePrintln("an error has curd");
                homeMenu.quid();
                return MenuExit.ERROR;
            } catch (InputMismatchException e) {
                homeMenu.getTerminalHelper().savePrintln("No Song whit this SongID");
                return null;
            }

            if (!homeMenu.getTui().mixPlayFromId(songId)) {
                homeMenu.getTerminalHelper().savePrintln("No Song whit SongID: " + songId);
            }
            return null;
        }
    },
    PLAY_FROM_HISTORY("ph", "Start Play from point in history") {
        @Override
        MenuExit action(HomeMenu homeMenu) {
            int historyPos;
            try {
                historyPos = homeMenu.getTerminalInput().getInt("History Number: ");
            } catch (IOException | InterruptedException e) {
                homeMenu.getTerminalHelper().savePrintln("an error has curd");
                homeMenu.quid();
                return MenuExit.ERROR;
            } catch (InputMismatchException e) {
                homeMenu.getTerminalHelper().savePrintln("No Song at this history point");
                return null;
            }

            if (!homeMenu.getTui().mixPlayFromHistory(historyPos)) {
                homeMenu.getTerminalHelper().savePrintln("No History point: " + historyPos);
            }
            return null;
        }
    },

    LIST_SONGS("ls", "List song") {
        @Override
        MenuExit action(HomeMenu homeMenu) {
            homeMenu.getTerminalHelper().savePrint(homeMenu.getSongList());
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
            homeMenu.getTerminalHelper().savePrint(homeMenu.getTui().getErrorLog());
            return null;
        }
    },

    VOLUME_DEFAULT("vd", "Set the default Volume [ Volume for Songs where the volume isn't set ]") {
        @Override
        MenuExit action(HomeMenu homeMenu) {
            int percent;
            try {
                percent = homeMenu.getTerminalInput().getInt("% ");

                if (!homeMenu.getMusicPlayer().setDefaultVolumePercent(percent)) {
                    homeMenu.getTerminalHelper().savePrintln("Pleas insert a value between 0 and 100");
                }

                return null;
            } catch (IOException | InterruptedException e) {
                homeMenu.getTerminalHelper().savePrintln("an error has curd");
                homeMenu.getTui().addToErrorLog("IOException @ SongOption @ VolumeDefault");
                homeMenu.quid();
                return MenuExit.ERROR;
            } catch (InputMismatchException e) {
                homeMenu.getTerminalHelper().savePrintln("This is not a Number");
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
                newDir = homeMenu.getTerminalInput().getString("Directory? " + workingDir);
            } catch (IOException | InterruptedException e) {
                homeMenu.getTerminalHelper().savePrintln(RED + "ERROR" + RESET);
                homeMenu.getTui().addToErrorLog("getting input didn't work");
                return null;
            }
            if (homeMenu.getTui().setDir(workingDir + newDir))
                homeMenu.genSongList();
            return null;
        }
    },
    RELOAD_DIR("rd", "Reload directory") {
        @Override
        MenuExit action(HomeMenu homeMenu){
            try {
                homeMenu.getTerminalHelper().savePrintln("Reloading Songs ...");
                String out = homeMenu.getMusicPlayer().reloadDir() ? "Done!" : "can not load Songs";
                homeMenu.genSongList();
                homeMenu.getTerminalHelper().savePrintln(out);
            } catch (IOException e) {
                homeMenu.getTerminalHelper().savePrintln(RED + "cant reload Directory" + RESET);
                homeMenu.getTui().addToErrorLog("cant reload Directory");
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
            homeMenu.getTerminalHelper().savePrintln(HELP_STRING);
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
            homeMenu.getTerminalHelper().savePrintln(MenuBase.getUnknownMsg());
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
        ReturnValue<HomeOption> b = OptionHelper.genHelpStringAndKeyMap(HomeOption.class);
        HELP_STRING = b.HELP_STRING;
        KEY_MAP = b.KEY_MAP;
    }
}