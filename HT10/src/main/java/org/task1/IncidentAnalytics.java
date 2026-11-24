package org.task1;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

public class IncidentAnalytics {

    private IncidentAnalytics() {
    }

    public static Duration totalDowntime(List<Incident> incidents) {
        Objects.requireNonNull(incidents, "Список инцидентов не может быть null");
        Duration total = Duration.ZERO;
        for (Incident incident : incidents) {
            Objects.requireNonNull(incident, "Инцидент не может быть null");
            total = total.plus(incident.duration());
        }
        return total;
    }

    public static Duration averageDowntime(List<Incident> incidents) {
        Objects.requireNonNull(incidents, "Список инцидентов не может быть null");
        if (incidents.isEmpty()) {
            return Duration.ZERO;
        }

        BigInteger nanosPerSecond = BigInteger.valueOf(1_000_000_000);
        BigInteger totalNanos = BigInteger.ZERO;
        for (Incident incident : incidents) {
            Objects.requireNonNull(incident, "Инцидент не может быть null");
            Duration duration = incident.duration();
            BigInteger nanos = BigInteger.valueOf(duration.getSeconds())
                    .multiply(nanosPerSecond)
                    .add(BigInteger.valueOf(duration.getNano()));
            totalNanos = totalNanos.add(nanos);
        }
        BigInteger averageNanos = totalNanos.divide(BigInteger.valueOf(incidents.size()));
        BigInteger[] secondsAndNanos = averageNanos.divideAndRemainder(nanosPerSecond);
        return Duration.ofSeconds(secondsAndNanos[0].longValueExact(), secondsAndNanos[1].longValueExact());
    }

    public static Optional<Incident> longestIncident(List<Incident> incidents) {
        Objects.requireNonNull(incidents, "Список инцидентов не может быть null");
        Comparator<Incident> longestFirst = Comparator.comparing(Incident::duration)
                .reversed()
                .thenComparing(Incident::startedAt)
                .thenComparing(Incident::id);

        Incident longest = null;
        for (Incident incident : incidents) {
            Objects.requireNonNull(incident, "Инцидент не может быть null");
            if (longest == null || longestFirst.compare(incident, longest) < 0) {
                longest = incident;
            }
        }
        return Optional.ofNullable(longest);
    }

    public static Map<LocalDate, List<Incident>> groupByStartDate(List<Incident> incidents, ZoneId zone) {
        Objects.requireNonNull(incidents, "Список инцидентов не может быть null");
        Objects.requireNonNull(zone, "Часовой пояс не может быть null");

        Map<LocalDate, List<Incident>> groups = new TreeMap<>();
        for (Incident incident : incidents) {
            Objects.requireNonNull(incident, "Инцидент не может быть null");
            LocalDate date = incident.startedAt().atZone(zone).toLocalDate();
            List<Incident> group = groups.computeIfAbsent(date, k -> new ArrayList<>());
            group.add(incident);
        }

        Comparator<Incident> byStart = Comparator.comparing(Incident::startedAt)
                .thenComparing(Incident::id);
        for (List<Incident> group : groups.values()) {
            group.sort(byStart);
        }
        return groups;
    }
}