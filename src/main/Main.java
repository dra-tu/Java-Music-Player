package main;

import musicPlayer.PlayerStarter;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

        System.out.println( Arrays.toString(args) );

        try {
            CliOptions options = new CliOptions(args);

            PlayerStarter starter = options.playerStarer.getDeclaredConstructor().newInstance();
            boolean worked = starter.start(args[args.length - 1], options.directStart);

            if (!worked)
                printUsage();

        } catch (UnknownCliOption | InvocationTargetException | NoSuchMethodException e) {
            printUsage();
        }
    }

    private static void printUsage() {
        System.out.println("""
                Usage: java -jar jmp.jar [option] [dir]
                
                option:
                    -p: start playing immediately
                    -g: use GUI
                    -t: use TUI
                
                dir: directory with the songs
                """);
    }
}
