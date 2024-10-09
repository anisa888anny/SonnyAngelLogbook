package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestLogbook {
    
    private Logbook testBook;

    @BeforeEach
    void runBefore() {
        testBook = new Logbook();
    }

    @Test
    void testAddLogEntry() {
        LogEntry entry = new LogEntry("Squid", 20211004, "added");
        testBook.addLogEntry(entry);
        List<String> availableAngels = testBook.getAvailableAngels();
        assertEquals(1, availableAngels.size());
        assertEquals("Squid", availableAngels.get(0));
    }

    @Test
    void testGetAvailableAngels() {
        LogEntry testEntry1 = new LogEntry("Apple", 20230413, "added");
        LogEntry testEntry2 = new LogEntry("Cabbage", 20240506, "added");
        LogEntry testEntry3 = new LogEntry("Apple", 20240101, "traded");


        testBook.addLogEntry(testEntry1);
        testBook.addLogEntry(testEntry2);
        testBook.addLogEntry(testEntry3);

        List<String> availableAngels = testBook.getAvailableAngels();
        assertEquals(1, availableAngels.size());
        assertTrue(availableAngels.contains("Cabbage"));
        assertFalse(availableAngels.contains("Apple"));
    }

    @Test
    public void testGetCollectionCount() {
        LogEntry testEntry1 = new LogEntry("Apple", 20230413, "added");
        LogEntry testEntry2 = new LogEntry("Cabbage", 20240506, "added");
        LogEntry testEntry3 = new LogEntry("Apple", 20240101, "sold");

        testBook.addLogEntry(testEntry1);
        testBook.addLogEntry(testEntry2);
        testBook.addLogEntry(testEntry3);

        assertEquals(1, testBook.getCollectionCount());

    }

    @Test
    public void testGetCollectionCountNoEntries() {
        assertEquals(0, testBook.getCollectionCount());
    }

    @Test
    public void testGetAvailableAngelsNoEntries() {
        List<String> availableAngels = testBook.getAvailableAngels();
        assertTrue(availableAngels.isEmpty());
    }

}
