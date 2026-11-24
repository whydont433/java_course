package org.task1;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public record Incident(String id, String service, Instant startedAt, Instant resolvedAt) {

    public Incident {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id не может быть пустым");
        }
        if (service == null || service.isBlank()) {
            throw new IllegalArgumentException("Название сервиса не может быть пустым");
        }
        Objects.requireNonNull(startedAt, "Начало инцидента не может быть null");
        Objects.requireNonNull(resolvedAt, "Окончание инцидента не может быть null");
        if (resolvedAt.isBefore(startedAt)) {
            throw new IllegalArgumentException("Окончание инцидента раньше начала");
        }
    }

    public Duration duration() {
        return Duration.between(startedAt, resolvedAt);
    }
}
