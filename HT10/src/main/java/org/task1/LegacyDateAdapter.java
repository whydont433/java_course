package org.task1;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public final class LegacyDateAdapter {

    private LegacyDateAdapter() {
    }

    public static Instant toInstant(Date date) {
        Objects.requireNonNull(date, "Дата не может быть null");
        return date.toInstant();
    }

    public static Date toDate(Instant instant) {
        Objects.requireNonNull(instant, "Момент времени не может быть null");
        return Date.from(instant);
    }

    public static ZonedDateTime toZonedDateTime(GregorianCalendar calendar) {
        Objects.requireNonNull(calendar, "Календарь не может быть null");
        return calendar.toZonedDateTime();
    }

    public static GregorianCalendar toGregorianCalendar(ZonedDateTime dateTime) {
        Objects.requireNonNull(dateTime, "Дата со временем и часовым поясом не может быть null");
        return GregorianCalendar.from(dateTime);
    }
}
