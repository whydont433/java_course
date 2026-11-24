package org.task1;

import java.time.Clock;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class MaintenanceScheduler {
    private final Clock clock;

    public MaintenanceScheduler(Clock clock) {
        this.clock = Objects.requireNonNull(clock, "Clock не может быть null");
    }

    public MaintenanceWindow nextWindow(
            LocalDate date, LocalTime localStart, Duration duration,
            ZoneId zone, OverlapPolicy policy) {
        Objects.requireNonNull(date, "Дата не может быть null");
        checkArguments(localStart, duration, zone, policy);

        LocalDateTime localDateTime = date.atTime(localStart);
        if (zone.getRules().getValidOffsets(localDateTime).isEmpty()) {
            throw new DateTimeException("Локальное время не существует: " + localDateTime + " (" + zone + ")");
        }

        ZonedDateTime zonedStart = localDateTime.atZone(zone);
        if (policy == OverlapPolicy.EARLIER) {
            zonedStart = zonedStart.withEarlierOffsetAtOverlap();
        } else {
            zonedStart = zonedStart.withLaterOffsetAtOverlap();
        }

        Instant start = zonedStart.toInstant();
        return new MaintenanceWindow(start, start.plus(duration));
    }

    public List<MaintenanceWindow> upcomingWindows(
            int days, LocalTime localStart, Duration duration,
            ZoneId zone, OverlapPolicy policy) {
        if (days < 0) {
            throw new IllegalArgumentException("Число дней не может быть отрицательным: " + days);
        }
        checkArguments(localStart, duration, zone, policy);
        if (days == 0) {
            return List.of();
        }

        LocalDate firstDate = LocalDate.now(clock.withZone(zone));
        List<MaintenanceWindow> windows = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            windows.add(nextWindow(firstDate.plusDays(i), localStart, duration, zone, policy));
        }
        return windows;
    }

    private static void checkArguments(
            LocalTime localStart, Duration duration, ZoneId zone, OverlapPolicy policy) {
        Objects.requireNonNull(localStart, "Время начала не может быть null");
        Objects.requireNonNull(duration, "Длительность не может быть null");
        Objects.requireNonNull(zone, "Часовой пояс не может быть null");
        Objects.requireNonNull(policy, "Политика выбора смещения не может быть null");
        if (duration.isZero() || duration.isNegative()) {
            throw new IllegalArgumentException("Длительность окна должна быть положительной");
        }
    }
}