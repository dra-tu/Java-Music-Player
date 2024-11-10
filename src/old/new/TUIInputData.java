package TUI;

import MusicPlayer.TimeStamp;
import TUI.Menus.SongOption;

public class TUIInputData {
    public SongOption songOption;
    public TimeStamp time;

    public TUIInputData() {
        songOption = SongOption.NONE;
        time = new TimeStamp(0L);
    }

    public TUIInputData(SongOption songOption, TimeStamp time) {
        this.songOption = songOption;
        this.time = time;
    }
}
