import TUI.TUI;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int maxLoadSongs = 3;
        String musicDir = "/home/dratu/Music/";

        TUI tui = new TUI(maxLoadSongs);
        tui.setDir(musicDir);
        tui.start();

    }
}