package tui.menus;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;

import musicPlayer.songTypes.LoadedSong;
import tui.menus.OptionHelper.ReturnValue;

public enum SongOption {
    // they are used but not directly
    PAUSE("p", "Pause") {
        @Override
        MenuExit action(SongMenu songMenu) {
            songMenu.musicPlayer.stopSong();
            return null;
        }
    },
    CONTINUE("o", "Continue") {
        @Override
        MenuExit action(SongMenu songMenu) {
            songMenu.musicPlayer.continueSong();
            return null;
        }
    },

    REWIND("r", "Rewind") {
        @Override
        MenuExit action(SongMenu songMenu) {
            // TODO: THIS try/catch is as in in SKIP and JUMP => put it in a Methode
            long jumpTime;
            try {
                jumpTime = songMenu.terminalInput.getTimeStamp();
            } catch (IOException | InterruptedException e) {
                songMenu.terminalHelper.savePrintln("an error has curd");
                songMenu.quid();
                return MenuExit.ERROR;
            } catch (InputMismatchException e) {
                songMenu.terminalHelper.savePrintln("This is not a Time");
                return null;
            }
            songMenu.musicPlayer.rewindTime(jumpTime);
            return null;
        }
    },
    SKIP("s", "Skips") {
        @Override
        MenuExit action(SongMenu songMenu) {
            long jumpTime;
            try {
                jumpTime = songMenu.terminalInput.getTimeStamp();
            } catch (IOException | InterruptedException e) {
                songMenu.terminalHelper.savePrintln("an error has curd");
                songMenu.quid();
                return MenuExit.ERROR;
            } catch (InputMismatchException e) {
                songMenu.terminalHelper.savePrintln("This is not a Time");
                return null;
            }
            songMenu.musicPlayer.skipTime(jumpTime);
            return null;
        }
    },

    JUMP("j", "Jump") {
        @Override
        MenuExit action(SongMenu songMenu) {
            long jumpTime;
            try {
                jumpTime = songMenu.terminalInput.getTimeStamp();
            } catch (IOException | InterruptedException e) {
                songMenu.terminalHelper.savePrintln("an error has curd");
                songMenu.quid();
                return MenuExit.ERROR;
            } catch (InputMismatchException e) {
                songMenu.terminalHelper.savePrintln("This is not a Time");
                return null;
            }
            songMenu.musicPlayer.jumpTo(jumpTime);
            return null;
        }
    },

    LIST_SONGS("ls", "List songs") {
        @Override
        MenuExit action(SongMenu songMenu) {
            songMenu.terminalHelper.savePrint(songMenu.songList);
            return null;
        }
    },
    LIST_HISTORY("lh", "list history") {
        @Override
        MenuExit action(SongMenu songMenu) {
            songMenu.printHistory();
            return null;
        }
    },
    LIST_ERRORS("le", "list errors") {
        @Override
        MenuExit action(SongMenu songMenu) {
            songMenu.terminalHelper.savePrint(songMenu.tui.getErrorLog());
            return null;
        }
    },

    VOLUME_SONG("vs", "Set the volume by percentile [ % ]") {
        @Override
        MenuExit action(SongMenu songMenu) throws IOException {
            LoadedSong currentSong = songMenu.musicPlayer.getCurrentSong();
            int percent;
            try {
                percent = songMenu.terminalInput.getInt("% ");
            } catch (IOException | InterruptedException e) {
                songMenu.terminalHelper.savePrintln("an error has curd");
                songMenu.quid();
                return MenuExit.ERROR;
            } catch (InputMismatchException e) {
                songMenu.terminalHelper.savePrintln("This is not a Number");
                return null;
            }

            if (!currentSong.setVolumePercent(percent))
                songMenu.terminalHelper.savePrintln("Pleas insert a value between 0 and 100");
            return null;
        }
    },

    NEXT("n", "Play next in History") {
        @Override
        MenuExit action(SongMenu songMenu) {
            songMenu.quid();
            return MenuExit.SONG_Next;
        }
    },
    BACK("b", "go Back in History") {
        @Override
        MenuExit action(SongMenu songMenu) {
            songMenu.quid();
            return MenuExit.SONG_BACK;
        }
    },

    CLEAR("c", "Clear") {
        @Override
        MenuExit action(SongMenu songMenu) {
            songMenu.clear();
            return null;
        }
    },
    HELP("?", "Help") {
        @Override
        MenuExit action(SongMenu songMenu) {
            songMenu.terminalHelper.savePrintln(HELP_STRING);
            return null;
        }
    },
    QUIT("q", "Quit") {
        @Override
        MenuExit action(SongMenu songMenu) {
            songMenu.quid();
            return MenuExit.USER_EXIT;
        }
    },

    NOT_AN_OPTION("", "") {
        @Override
        MenuExit action(SongMenu songMenu) {
            songMenu.terminalHelper.savePrintln(MenuBase.unknownMsg);
            return null;
        }
    };

    // this part is the same in all Option Enums
    // EXCEPTIONS: Name of constructor, Type in HasMap, in static for loop, return Type of getByKey
    final String KEY;
    final String DESCRIPTION;

    abstract MenuExit action(SongMenu songMenu) throws IOException;

    private static final HashMap<String, SongOption> KEY_MAP;
    static final String HELP_STRING;

    SongOption(String KEY, String DESCRIPTION) {
        this.KEY = KEY;
        this.DESCRIPTION = DESCRIPTION;
    }

    static SongOption getByKey(String key) {
        SongOption option = KEY_MAP.get(key);
        return option == null
                ? SongOption.NOT_AN_OPTION
                : option;
    }

    static {
        ReturnValue<SongOption> b = OptionHelper.genHelpStringAndKeyMap(SongOption.class);
        HELP_STRING = b.HELP_STRING;
        KEY_MAP = b.KEY_MAP;
    }
}