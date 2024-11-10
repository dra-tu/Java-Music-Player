package TUI;

import MusicPlayer.MusicPlayer;
import MusicPlayer.TimeStamp;
import TUI.Menus.MainMenu;
import TUI.Menus.SongMenu;
import TUI.Menus.SongOption;

public class TUIMenuMgr implements Runnable {
    SongMenu songMenu;
    MainMenu mainMenu;

//    TODO: use The Manu Classes

    public TUIMenuMgr(TerminalPosition startPos, MusicPlayer musicPlayer, TerminalLock termLock) {
        Share share = new Share(termLock);
        InputFunc inFunc = new InputFunc(termLock);

        songMenu = new SongMenu(startPos, musicPlayer, termLock, share, inFunc);
        mainMenu = new MainMenu(startPos, musicPlayer, termLock, share, inFunc);
    }

    @Override
    public void run() {
        songMenu.start();
    }
}
