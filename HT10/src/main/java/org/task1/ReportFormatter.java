package org.task1;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public final class ReportFormatter {

    private ReportFormatter() {
    }

    public static String formatWindow(MaintenanceWindow window, ZoneId zone, Locale locale) {
        Objects.requireNonNull(window, "Окно обслуживания не может быть null");
        Objects.requireNonNull(zone, "Часовой пояс не может быть null");
        Objects.requireNonNull(locale, "Locale не может быть null");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm XXX (VV)", locale)
                .withZone(zone);
        return formatter.format(window.start()) + " – " + formatter.format(window.end());
    }
}
