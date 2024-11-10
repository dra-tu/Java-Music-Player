package TUI;

import MusicPlayer.TimeStamp;
import TUI.Menus.SongOption;

public class TUIInputDataControl {
    private TUIInputData data;
    private boolean sent;

    public TUIInputDataControl() {
        data = new TUIInputData();
        sent = false;
    }

    public synchronized TUIInputData receive() {
        while(!sent) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Interrupt");
            }
        }

        sent = false;
        notifyAll();
        return data;
    }

    public synchronized void send(SongOption songOption, TimeStamp time) {
        while(sent) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Interrupt");
            }
        }

        sent = true;

        data = new TUIInputData(songOption, time);
        notifyAll();
    }

    public synchronized void send(SongOption songOption) {
        send(songOption, new TimeStamp(0L));
    }
}
