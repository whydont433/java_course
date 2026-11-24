package org.task1;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportAndLegacyTest {

    @Test
    void formatsBothTimesInRequestedZone() {
        MaintenanceWindow window = new MaintenanceWindow(
                Instant.parse("2024-03-31T01:00:00Z"), Instant.parse("2024-03-31T02:00:00Z"));

        assertEquals("31.03.2024 03:00 +02:00 (Europe/Berlin) – 31.03.2024 04:00 +02:00 (Europe/Berlin)",
                ReportFormatter.formatWindow(window, ZoneId.of("Europe/Berlin"), Locale.ROOT));
    }

    @Test
    void dateRoundTripPreservesInstant() {
        Instant instant = Instant.parse("2024-03-31T01:00:00.123Z");
        Date date = LegacyDateAdapter.toDate(instant);

        assertEquals(instant, LegacyDateAdapter.toInstant(date));
    }
}