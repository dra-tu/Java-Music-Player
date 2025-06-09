import gui.Gui;
import musicPlayer.PlayerStarter;
import tui.TUI;

public class Main {
    public static void main(String[] args) {
        PlayerStarter ps;

        if (args[0].equals("t")) {
            ps = new TUI();
        } else if (args[0].equals("g")) {
            ps = new Gui();
        } else {
            printUsage();
            return;
        }

        boolean worked = ps.start(args[1]);

        if (!worked) printUsage();
    }

    private static void printUsage() {
        System.out.println("""
                Usage: java -jar jmp.jar [mode] [MUSIC DIRECTORY]
                
                mode:
                    g: use GUI
                    t: use TUI
                
                MUSIC DIRECTORY: directory with the songs
                """);
    }
}
