package old.Terminal.UI;

import java.util.Scanner;

import static old.Share.MyPrint.print;

public class TerminalShare {
    static String[] getInput() {
        print("> ");

        Scanner sc = new Scanner(System.in);
        String option = sc.next();
        // sc.close();

        return option.split("-");
    }
}
