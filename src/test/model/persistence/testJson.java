package model.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.LogEntry;

public class TestJson {

    // EFFECTS: checks that the details of a LogEntry match the expected values
    protected void checkLogEntry(String expectedAngelName, int expectedDate, String expectedTransactionType,
            LogEntry logEntry) {
        assertEquals(expectedAngelName, logEntry.getAngelName());
        assertEquals(expectedDate, logEntry.getDate());
        assertEquals(expectedTransactionType, logEntry.getTransactionType());
    }

}
