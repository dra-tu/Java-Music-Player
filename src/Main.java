import tui.TUI;
import gui.GUI;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        if(args.length != 2) {
            printUsage();
            return;
        }

        if(Objects.equals(args[0], "-t")) {
            TUI tui = new TUI();
            tui.start(args[1]);
        } else if (Objects.equals(args[0], "-g")) {
            GUI gui = new GUI();
            gui.start();
        }else {
            printUsage();
        }
    }

    private static void printUsage() {
        System.out.println("""
                    Usage: jmp.jar [mode] [dir]
                    
                    mode:
                    -t: tui
                    -g: gui
                    
                    dir: directory whit the songs
                    """);
    }
}
