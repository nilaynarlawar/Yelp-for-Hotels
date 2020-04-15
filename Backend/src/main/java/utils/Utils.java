package utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Utils {
    public static Timestamp getCurrentSQLTimestamp() {
        Instant i = Instant.now();
        return new Timestamp(i.toEpochMilli());
    }

    public static Timestamp getFutureTimestamp(int minutes) {
        Timestamp ts = getCurrentSQLTimestamp();
        ts.setTime(ts.getTime() + getTimeInMiliSeconds(minutes));
        return ts;
    }

    private static long getTimeInMiliSeconds(int minutes) {
        return minutes * 60 * 1000;
    }

    public static ZonedDateTime toZDT(Timestamp ts) {
        if (ts == null) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(ts.getTime());
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        return zonedDateTime;
    }
}

