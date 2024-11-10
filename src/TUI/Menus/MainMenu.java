package TUI.Menus;

import MusicPlayer.MusicPlayer;
import TUI.InputFunc;
import TUI.Share;
import TUI.TerminalLock;
import TUI.TerminalPosition;

public class MainMenu extends Menu {

    public MainMenu(
            TerminalPosition startPos,
            MusicPlayer musicPlayer,
            TerminalLock termLock,
            Share share,
            InputFunc inputFunc
    ) {
        super(startPos, musicPlayer, termLock, share, inputFunc);
    }

    @Override
    void setUp() {

    }

    @Override
    void action() {
    }
}
