import tui.TUI;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            printUsage();
            return;
        }

        TUI tui = new TUI();
        tui.start(args[0]);
    }

    private static void printUsage() {
        System.out.println("""
                Usage: java -jar jmp.jar [dir]
                
                dir: directory whit the songs
                """);
    }
}
