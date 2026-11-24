package org.task1;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaintenanceSchedulerTest {

    private final ZoneId berlin = ZoneId.of("Europe/Berlin");
    private final MaintenanceScheduler scheduler = new MaintenanceScheduler(
            Clock.fixed(Instant.parse("2024-03-29T23:30:00Z"), ZoneOffset.UTC));

    @Test
    void rejectsTimeInGap() {
        assertThrowsExactly(DateTimeException.class, () -> scheduler.nextWindow(
                LocalDate.of(2024, 3, 31), LocalTime.of(2, 30),
                Duration.ofMinutes(30), berlin, OverlapPolicy.EARLIER));
    }

    @Test
    void choosesOffsetInOverlap() {
        LocalDate date = LocalDate.of(2024, 10, 27);
        LocalTime localStart = LocalTime.of(2, 30);
        Duration hour = Duration.ofHours(1);
        MaintenanceWindow earlier = scheduler.nextWindow(date, localStart, hour, berlin, OverlapPolicy.EARLIER);
        MaintenanceWindow later = scheduler.nextWindow(date, localStart, hour, berlin, OverlapPolicy.LATER);

        assertEquals(ZoneOffset.ofHours(2), earlier.start().atZone(berlin).getOffset());
        assertEquals(ZoneOffset.ofHours(1), later.start().atZone(berlin).getOffset());
        assertEquals(hour, Duration.between(earlier.start(), later.start()));
    }

    @Test
    void dailyWindowsKeepLocalStartAcrossDstChange() {
        LocalTime localStart = LocalTime.of(3, 30);
        Duration duration = Duration.ofMinutes(30);
        List<MaintenanceWindow> windows = scheduler.upcomingWindows(
                2, localStart, duration, berlin, OverlapPolicy.EARLIER);

        assertEquals(2, windows.size());
        Instant firstStart = windows.get(0).start();
        Instant secondStart = windows.get(1).start();

        assertEquals(LocalDate.of(2024, 3, 30).atTime(localStart),
                firstStart.atZone(berlin).toLocalDateTime());
        assertEquals(localStart, secondStart.atZone(berlin).toLocalTime());
        assertEquals(Duration.ofHours(23), Duration.between(firstStart, secondStart));
        assertEquals(windows, scheduler.upcomingWindows(2, localStart, duration, berlin, OverlapPolicy.EARLIER));
    }
}