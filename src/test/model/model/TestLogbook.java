package model.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.LogEntry;
import model.Logbook;

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

    @Test
    void testGetAllEntries() {
        LogEntry testEntry1 = new LogEntry("Apple", 20230413, "added");
        LogEntry testEntry2 = new LogEntry("Cabbage", 20240506, "traded");
        LogEntry testEntry3 = new LogEntry("Star", 20240101, "sold");

        testBook.addLogEntry(testEntry1);
        testBook.addLogEntry(testEntry2);
        testBook.addLogEntry(testEntry3);

        List<LogEntry> allEntries = testBook.getAllEntries();

        assertEquals(3, allEntries.size());

        assertEquals("Apple", allEntries.get(0).getAngelName());
        assertEquals(20230413, allEntries.get(0).getDate());
        assertEquals("added", allEntries.get(0).getTransactionType());

        assertEquals("Cabbage", allEntries.get(1).getAngelName());
        assertEquals(20240506, allEntries.get(1).getDate());
        assertEquals("traded", allEntries.get(1).getTransactionType());

        assertEquals("Star", allEntries.get(2).getAngelName());
        assertEquals(20240101, allEntries.get(2).getDate());
        assertEquals("sold", allEntries.get(2).getTransactionType());

    }

    @Test
    void testRateAngel() {
        LogEntry testEntry1 = new LogEntry("Star", 20230202, "added");
        testBook.addLogEntry(testEntry1);
        testBook.rateAngel("Star", 5);
        testBook.rateAngel("Star", 4);
        assertEquals(4.5, testEntry1.getAverageRating());
    }

    @Test
    void testGetAverageCollectionRatingNoEntries() {
        assertEquals(0.0, testBook.getAverageCollectionRating());
    }

    @Test
    void testGetAverageCollectionRatingMultipleEntries() {
        LogEntry testEntry1 = new LogEntry("Apple", 20230413, "added");
        LogEntry testEntry2 = new LogEntry("Cabbage", 20240506, "traded");
        testBook.addLogEntry(testEntry2);
        testBook.addLogEntry(testEntry1);

        testBook.rateAngel("Apple", 5);
        testBook.rateAngel("Cabbage", 3);

        assertEquals(4.0, testBook.getAverageCollectionRating());

    }

    @Test
    void testGetAverageCollectionRatingSomeNotRated() {
        LogEntry testEntry1 = new LogEntry("Apple", 20230413, "added");
        LogEntry testEntry2 = new LogEntry("Cabbage", 20240506, "traded");
        testBook.addLogEntry(testEntry2);
        testBook.addLogEntry(testEntry1);

        testBook.rateAngel("Apple", 5);
        assertEquals(5, testBook.getAverageCollectionRating());
    }

    @Test
    void testRateAngelNotInCollection() {
        testBook.rateAngel("DNE", 4);
        assertEquals(0, testBook.getAverageCollectionRating());
    }

    @Test
    void testGetAvailableAngelsWithSoldOrTraded() {
        LogEntry testEntry1 = new LogEntry("Apple", 20230413, "added");
        LogEntry testEntry2 = new LogEntry("Cabbage", 20240506, "added");
        LogEntry testEntry3 = new LogEntry("Apple", 20240101, "sold");
        LogEntry testEntry4 = new LogEntry("Cabbage", 20240506, "traded");
        LogEntry testEntry5 = new LogEntry("Star", 20230303, "added");
        LogEntry testEntry6 = new LogEntry("Apple", 20230413, "added");

        testBook.addLogEntry(testEntry1);
        testBook.addLogEntry(testEntry3);
        List<String> availableAngels = testBook.getAvailableAngels();
        assertTrue(availableAngels.isEmpty());

        testBook.addLogEntry(testEntry2);
        testBook.addLogEntry(testEntry4);
        List<String> availableAngels2 = testBook.getAvailableAngels();
        assertTrue(availableAngels2.isEmpty());

        testBook.addLogEntry(testEntry5);
        availableAngels = testBook.getAvailableAngels();
        assertEquals(1, availableAngels.size());
        assertTrue(availableAngels.contains("Star"));
        assertFalse(availableAngels.contains("Apple"));

        testBook.addLogEntry(testEntry6);
        availableAngels = testBook.getAvailableAngels();
        assertTrue(availableAngels.contains("Apple"));

    }

    @Test
    void testGetAverageCollectionRatingWithNoRatings() {
        LogEntry testEntry1 = new LogEntry("Apple", 20230413, "added");
        LogEntry testEntry2 = new LogEntry("Cabbage", 20240506, "added");
        testBook.addLogEntry(testEntry1);
        testBook.addLogEntry(testEntry2);
        assertEquals(0.0, testBook.getAverageCollectionRating());
    }

    @Test
    void testToJson() {
        // Setup some log entries
        LogEntry testEntry1 = new LogEntry("Apple", 20230413, "added");
        LogEntry testEntry2 = new LogEntry("Cabbage", 20240506, "sold");
        LogEntry testEntry3 = new LogEntry("Star", 20240101, "traded");

        // Add entries to the logbook
        testBook.addLogEntry(testEntry1);
        testBook.addLogEntry(testEntry2);
        testBook.addLogEntry(testEntry3);

        // Convert logbook to JSON
        JSONObject json = testBook.toJson();

        // Verify JSON structure
        JSONArray entriesArray = json.getJSONArray("entries");
        assertEquals(3, entriesArray.length()); // Verify 3 entries in JSON

        // Verify each entry's details
        JSONObject jsonEntry1 = entriesArray.getJSONObject(0);
        assertEquals("Apple", jsonEntry1.getString("angelName"));
        assertEquals(20230413, jsonEntry1.getInt("date"));
        assertEquals("added", jsonEntry1.getString("transactionType"));

        JSONObject jsonEntry2 = entriesArray.getJSONObject(1);
        assertEquals("Cabbage", jsonEntry2.getString("angelName"));
        assertEquals(20240506, jsonEntry2.getInt("date"));
        assertEquals("sold", jsonEntry2.getString("transactionType"));

        JSONObject jsonEntry3 = entriesArray.getJSONObject(2);
        assertEquals("Star", jsonEntry3.getString("angelName"));
        assertEquals(20240101, jsonEntry3.getInt("date"));
        assertEquals("traded", jsonEntry3.getString("transactionType"));
    }

}
