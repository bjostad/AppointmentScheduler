package utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author BJ Bjostad
 */
public class Time {
    /**
     * Calculates offset from system default timezone to provided timezone
     * @param tz timezone to calculate offset from
     * @return int offset in hours
     */
    public static int offsetTo(String tz){
        return ZonedDateTime.now(ZoneId.systemDefault()).getHour()
                - ZonedDateTime.now(ZoneId.of(tz)).getHour();
    }

    /**
     * Convert LocalDateTime from system time to UTC
     * @param localDateTime time in system default timezone
     * @return LocalDateTime in UTC
     */
    public static LocalDateTime convertToUTC(LocalDateTime localDateTime) {
        ZonedDateTime originalZDT = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZDT = originalZDT.withZoneSameInstant(ZoneId.of("Etc/UTC"));
        return utcZDT.toLocalDateTime();
    }

    /**
     * convert LocalDateTime from UTC to system time
     * @param localDateTime time in UTC timezone
     * @return LocalDateTime time in system default timezone
     */
    public static LocalDateTime convertFromUTC(LocalDateTime localDateTime) {
        ZonedDateTime originalZDT = localDateTime.atZone(ZoneId.of("Etc/UTC"));
        ZonedDateTime utcZDT = originalZDT.withZoneSameInstant(ZoneId.systemDefault());
        return utcZDT.toLocalDateTime();
    }
}
