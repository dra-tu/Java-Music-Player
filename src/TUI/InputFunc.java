package TUI;

import MusicPlayer.TimeStamp;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class InputFunc {
    Scanner scanner;
    Share share;

    public InputFunc(TerminalLock termLock) {
        scanner = new Scanner(System.in).useLocale(Locale.ENGLISH);
        share = new Share(termLock);
    }

    public String getString(String prompt) throws IOException, InterruptedException {
        share.savePrint(prompt);
        waitForStuff();
        return scanner.next();
    }

    public int getInt(String prompt) throws IOException, InterruptedException {
        share.savePrint(prompt);
        waitForStuff();
        return scanner.nextInt();
    }

    public float getFloat(String prompt) throws IOException, InterruptedException {
        share.savePrint(prompt);
        waitForStuff();
        return scanner.nextFloat();
    }

    public TimeStamp getTimeStamp() throws IOException, InterruptedException {
        int minute = getInt("minute? ");
        int second = getInt("second? ");

        return new TimeStamp(minute, second);
    }

    private void waitForStuff() throws IOException, InterruptedException {
        while (System.in.available() == 0) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new InterruptedException("Interabted while reading input");
            }
        }


    }
}
