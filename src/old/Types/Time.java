package old.Types;

public class Time {
    long minutes;
    long seconds;

    public Time(long minutes, long seconds) {
        this.minutes = seconds;
        this.seconds = seconds;
    }

    public static Time fromMicroseconds(long microseconds) {
        return new Time(
                microseconds / (60_000_000),
                microseconds % (60_000_000)
        );
    }

    public static Time fromSeconds(long seconds) {
        return new Time(
                seconds / 60,
                seconds % 60
        );
    }

    public long toMicroseconds() {
        return (minutes * 60L + seconds) * 1000000L;
    }

    public static Time subtrsct(Time time1, Time time2) {
        return new Time(
                time1.minutes - time2.minutes,
                time1.seconds - time2.seconds
        );
    }
}
