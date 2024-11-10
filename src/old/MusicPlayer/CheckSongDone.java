package old.MusicPlayer;

import javax.sound.sampled.Clip;

public class CheckSongDone implements Runnable {
    private Clip clip;
    private long length;

    public CheckSongDone(Clip clip) {
        this.clip = clip;
        this.length = this.clip.getMicrosecondLength();
    }

    @Override
    public void run() {
        while (length < clip.getMicrosecondPosition()) {
            try {
                wait(1500); // 1.5s
            } catch (InterruptedException _){}
        }
        System.out.println("main.Song done");
    }
}
/*

 public class Foo implements Runnable {
     private volatile int value;

     @Override
     public void run() {
        value = 2;
     }

     public int getValue() {
         return value;
     }
 }

 */