package utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author BJ Bjostad
 */
public class Time {

    public static int offsetTo(String tz){
        return ZonedDateTime.now(ZoneId.systemDefault()).getHour()
                - ZonedDateTime.now(ZoneId.of(tz)).getHour();
    }

    public static LocalDateTime convertToUTC(LocalDateTime localDateTime) {
        ZonedDateTime originalZDT = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZDT = originalZDT.withZoneSameInstant(ZoneId.of("Etc/UTC"));
        return utcZDT.toLocalDateTime();
    }

    public static LocalDateTime convertFromUTC(LocalDateTime localDateTime) {
        ZonedDateTime originalZDT = localDateTime.atZone(ZoneId.of("Etc/UTC"));
        ZonedDateTime utcZDT = originalZDT.withZoneSameInstant(ZoneId.systemDefault());
        return utcZDT.toLocalDateTime();
    }
}
