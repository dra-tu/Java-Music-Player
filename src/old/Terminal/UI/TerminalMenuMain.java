package old.Terminal.UI;

import old.MusicPlayer.MusicPlayerControls;
import old.MusicPlayer.MusicPlayer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

import static old.Share.MyPrint.drawLine;
import static old.Share.MyPrint.println;
import static old.Terminal.UI.TerminalShare.getInput;

public class TerminalMenuMain implements Runnable {
    MusicPlayer musicPlayer;
    MusicPlayerControls playerControls;

    // MainMenu Options
    final static char MAIN_HELP = 'h';
    final static char MAIN_LIST = 'l';
    final static char MAIN_MIX = 'm';
    final static char MAIN_PLAY = 'p';
    final static char MAIN_RELOAD = 'r';
    final static char MAIN_QUIT = 'q';

    // constructor
    public TerminalMenuMain(MusicPlayer musicPlayer) {
        this.playerControls = musicPlayer.getControls();
        this.musicPlayer = musicPlayer;
    }

    @Override
    public void run() {
        printMainMenuOptions();
        mainControl();
    }

    // Menus
    void printMainMenuOptions() {
        drawLine();

        println("h: Display this menu");
        println("l: List all songs");
        println("m: Mix play");
        println("p-<number>: Play main.Song whit <number>");
        println("r: Reload songs");
        println("q: Quit");
    }

    // Menu Switches
    public void mainControl() {
        while (true) {
            String[] option = getInput();

            switch (option[0].charAt(0)) {
                case MAIN_HELP:
                    printMainMenuOptions();
                    break;
                case MAIN_LIST:
                    listSongs();
                    break;
                case MAIN_MIX:
                    playerControls.mixedPlay();
                    break;
                case MAIN_PLAY:
                    playerControls.playSong(Integer.parseInt(option[1]), true);
                    break;
                case MAIN_QUIT:
                    return;
                case MAIN_RELOAD:
                    println("Reloading...");
                    try {
                        playerControls.loadSongs();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                        println("Cant load songs!!!!");
                        break;
                    }
                    println("Done");
                    break;
                default:
                    println("=> unknown option");
                    break;
            }
            drawLine();
        }
    }

    private void listSongs() {
        for (int i = 0; i < musicPlayer.getSongsLength(); i++) {
            println(i + ": " + musicPlayer.getSongName(i));
        }
    }
}
