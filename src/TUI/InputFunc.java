package TUI;

import MusicPlayer.TimeStamp;

import java.util.Locale;
import java.util.Scanner;

public class InputFunc {
    Scanner scanner;
    Share share;

    public InputFunc(TerminalLock termLock) {
        scanner = new Scanner(System.in).useLocale(Locale.ENGLISH);
        share = new Share(termLock);
    }

    public String getString(String prompt) {
        share.savePrint(prompt);
        return scanner.next();
    }

    public int getInt(String prompt) {
        share.savePrint(prompt);
        return scanner.nextInt();
    }

    public float getFloat(String prompt) {
        share.savePrint(prompt);
        return scanner.nextFloat();
    }

    public TimeStamp getTimeStamp() {
        int minute = getInt("minute? ");
        int second = getInt("second? ");

        return new TimeStamp(minute, second);
    }
}
