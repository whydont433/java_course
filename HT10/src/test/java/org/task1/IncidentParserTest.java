package org.task1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class IncidentParserTest {

    @Test
    void parsesIncidentAndCalculatesDuration() {
        Incident incident = IncidentParser.parse("INC-1;billing;2024-03-30T22:30:00Z;2024-03-31T01:00:00Z");

        assertEquals(new Incident("INC-1", "billing",
                Instant.parse("2024-03-30T22:30:00Z"), Instant.parse("2024-03-31T01:00:00Z")), incident);
        assertEquals(Duration.ofMinutes(150), incident.duration());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "INC-1;billing;2024-03-30T22:30:00;2024-03-31T01:00:00Z",
            ";billing;2024-03-30T22:30:00Z;2024-03-31T01:00:00Z",
            "INC-1;billing;2024-03-31T01:00:00Z;2024-03-30T22:30:00Z"
    })
    void rejectsInvalidLine(String line) {
        assertThrows(IllegalArgumentException.class, () -> IncidentParser.parse(line));
    }
}