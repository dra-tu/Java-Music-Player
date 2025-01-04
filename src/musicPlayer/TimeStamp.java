package musicPlayer;

public class TimeStamp {
    public static long toMicroseconds(long minutes, long seconds) {
        return (minutes * 60L + seconds) * 1_000_000L;
    }

    public static String toString(long microseconds) {
        int minutes = (int) (microseconds / 1_000_000) / 60;
        int seconds = (int) (microseconds / 1_000_000) % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }
}
