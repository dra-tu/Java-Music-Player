import tui.TUI;

public class Main {
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Pleas insert Dir");
            return;
        }

        TUI tui = new TUI();
        tui.start(args[0]);

    }
}
