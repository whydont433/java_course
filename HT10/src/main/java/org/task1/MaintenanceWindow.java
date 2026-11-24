package org.task1;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public record MaintenanceWindow(Instant start, Instant end) {

    public MaintenanceWindow {
        Objects.requireNonNull(start, "Начало окна не может быть null");
        Objects.requireNonNull(end, "Окончание окна не может быть null");
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("Окончание окна должно быть позже начала");
        }
    }

    public Duration duration() {
        return Duration.between(start, end);
    }
}
