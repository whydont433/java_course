package org.task1;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class IncidentAnalyticsTest {

    @Test
    void calculatesDowntimeAndRoundsAverageDown() {
        Incident longer = new Incident("INC-1", "billing", Instant.EPOCH, Instant.EPOCH.plusNanos(4));
        Incident shorter = new Incident("INC-2", "gateway", Instant.EPOCH, Instant.EPOCH.plusNanos(3));
        List<Incident> incidents = List.of(shorter, longer);

        assertEquals(Duration.ofNanos(7), IncidentAnalytics.totalDowntime(incidents));
        assertEquals(Duration.ofNanos(3), IncidentAnalytics.averageDowntime(incidents));
        assertEquals(Optional.of(longer), IncidentAnalytics.longestIncident(incidents));
    }

    @Test
    void handlesEmptyList() {
        List<Incident> incidents = List.of();

        assertEquals(Duration.ZERO, IncidentAnalytics.totalDowntime(incidents));
        assertEquals(Duration.ZERO, IncidentAnalytics.averageDowntime(incidents));
        assertTrue(IncidentAnalytics.longestIncident(incidents).isEmpty());
    }

    @Test
    void groupsByBerlinDate() {
        Incident incident = IncidentParser.parse("INC-2;gateway;2024-03-30T23:30:00Z;2024-03-31T00:15:00Z");
        Map<LocalDate, List<Incident>> groups = IncidentAnalytics.groupByStartDate(
                List.of(incident), ZoneId.of("Europe/Berlin"));

        assertEquals(Map.of(LocalDate.of(2024, 3, 31), List.of(incident)), groups);
    }
}