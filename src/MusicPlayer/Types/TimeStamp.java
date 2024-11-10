package MusicPlayer.Types;

public class TimeStamp implements Comparable<TimeStamp> {
    int minutes;
    int seconds;

    public TimeStamp(long microseconds) {
        minutes =  (int) ( microseconds / 1_000_000 ) / 60;
        seconds =  (int) ( microseconds / 1_000_000 ) % 60;
    }
    public TimeStamp(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public void set(long microseconds) {
        minutes =  (int) ( microseconds / 1_000_000 ) / 60;
        seconds =  (int) ( microseconds / 1_000_000 ) % 60;
    }

    public void set(TimeStamp timeStamp) {
        minutes =  timeStamp.minutes;
        seconds =  timeStamp.seconds;
    }

    public String getFormatted() {
        return String.format("%02d:%02d", minutes, seconds);
    }

    public long toMicroseconds() {
        return (minutes * 60L + seconds) * 1_000_000L;
    }

    static public TimeStamp add(TimeStamp stamp1, TimeStamp stamp2) {
        return new TimeStamp(
                stamp1.minutes + stamp2.minutes,
                stamp1.seconds + stamp2.seconds
        );
    }

    static public TimeStamp subtract(TimeStamp stamp1, TimeStamp stamp2) {
        return new TimeStamp(
                stamp1.minutes - stamp2.minutes,
                stamp1.seconds - stamp2.seconds
        );
    }

    @Override
    public int compareTo(TimeStamp o) {
        return Long.compare(this.toMicroseconds(), o.toMicroseconds());
    }
}
