package org.task1;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public final class IncidentParser {

    private IncidentParser() {
    }

    public static Incident parse(String line) {
        Objects.requireNonNull(line, "Строка инцидента не может быть null");
        String[] fields = line.split(";", -1);
        if (fields.length != 4) {
            throw new IllegalArgumentException("В строке инцидента должно быть четыре поля");
        }
        try {
            return new Incident(fields[0], fields[1], Instant.parse(fields[2]), Instant.parse(fields[3]));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Некорректная метка времени: \"" + e.getParsedString() + "\"", e);
        }
    }
}
