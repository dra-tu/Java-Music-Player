package TUI;

public class TimeStamp implements Comparable<TimeStamp> {
    long microseconds;

    public TimeStamp(int minutes, int seconds) {
        this.microseconds = (minutes * 60L + seconds) * 1_000_000L;
    }

    public static String format(long microseconds) {
        int minutes = (int) ( microseconds / 1_000_000 ) / 60;
        int seconds = (int) ( microseconds / 1_000_000 ) % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    public long getMicroseconds() {
        return microseconds;
    }

    @Override
    public int compareTo(TimeStamp o) {
        return Long.compare(this.getMicroseconds(), o.getMicroseconds());
    }
}
