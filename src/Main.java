import tui.TUI;

public class Main {
    public static void main(String[] args) {
        if (args.length > 2 || args.length < 1) {
            printUsage();
            return;
        }

        boolean directStart = args[0].equals("-p");

        TUI tui = new TUI();
        boolean worked = tui.start(args[args.length-1],directStart);

        if(!worked)
            printUsage();
    }

    private static void printUsage() {
        System.out.println("""
                Usage: java -jar jmp.jar [option] [dir]
                
                option:
                    -p: start playing immediately
                
                dir: directory with the songs
                """);
    }
}
