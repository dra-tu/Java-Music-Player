package old.MusicPlayer;

public class MyException extends Throwable {
    String msg;

    MyException(String msg) {
        this.msg = msg;
    }
}
