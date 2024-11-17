import TUI.TUI;

public class Main {
    public static void main(String[] args) {
        int maxLoadSongs = 3;

        if(args.length == 0) {
            System.out.println("Pleas insert Dir");
            return;
        }

        TUI tui = new TUI(maxLoadSongs);
        tui.start(args[0]);

    }
}
