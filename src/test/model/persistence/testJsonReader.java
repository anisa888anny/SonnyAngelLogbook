package model.persistence;

import model.LogEntry;
import model.Logbook;
import persistence.JsonReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonReader {

    Logbook logbook = new Logbook(); // Instance of Logbook to add parsed entries.

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json"); // Non-existent file path.
        try {
            ArrayList<LogEntry> logEntries = reader.read(); // Try to read from a non-existent file.
            for (LogEntry entry : logEntries) {
                logbook.addLogEntry(entry);
            }
            fail("IOException was expected"); // Fail if no exception is thrown.
        } catch (IOException e) {
            // Pass: Expected exception is thrown.
        }
    }

    @Test
    void testReaderEmptyLogbook() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLogbook.json"); // Test file that is empty.
        try {
            ArrayList<LogEntry> logEntries = reader.read();
            for (LogEntry entry : logEntries) {
                logbook.addLogEntry(entry); // Add the entries to the logbook.
            }
            assertEquals(0, logbook.getAllEntries().size()); // Verify that the logbook is still empty.
        } catch (IOException e) {
            fail("Couldn't read from file"); // Fail if an exception is thrown.
        }
    }

    @Test
    void testReaderGeneralLogbook() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralLogbook.json"); // Test file with general entries.
        try {
            ArrayList<LogEntry> logEntries = reader.read(); // Parse the JSON data.
            for (LogEntry entry : logEntries) {
                logbook.addLogEntry(entry); // Add each parsed entry to the logbook.
            }
            assertEquals(2, logbook.getAllEntries().size()); // Verify the size of the logbook.

            // Check the first log entry details.
            LogEntry entry1 = logbook.getAllEntries().get(0);
            assertEquals("Angel1", entry1.getAngelName()); // Verify the name.
            assertEquals(20230101, entry1.getDate()); // Verify the date.
            assertEquals("added", entry1.getTransactionType()); // Verify the transaction type.

            // Check the second log entry details.
            LogEntry entry2 = logbook.getAllEntries().get(1);
            assertEquals("Angel2", entry2.getAngelName()); // Verify the name.
            assertEquals(20230102, entry2.getDate()); // Verify the date.
            assertEquals("sold", entry2.getTransactionType()); // Verify the transaction type.

        } catch (IOException e) {
            fail("Couldn't read from file"); // Fail if an exception is thrown.
        }
    }

    @Test
    void testParseLogEntry() {
        try {
            // Set up the JSON object with test data.
            JSONObject jsonEntry = new JSONObject();
            jsonEntry.put("angelName", "TestAngel");
            jsonEntry.put("date", 20231010);
            jsonEntry.put("transactionType", "added");
            jsonEntry.put("ratings", new JSONArray("[5, 4, 3]"));

            JsonReader reader = new JsonReader("./data/dummy.json");
            Method method = JsonReader.class.getDeclaredMethod("parseLogEntry", JSONObject.class);
            method.setAccessible(true);

            LogEntry logEntry = (LogEntry) method.invoke(reader, jsonEntry);

            assertEquals("TestAngel", logEntry.getAngelName());
            assertEquals(20231010, logEntry.getDate());
            assertEquals("added", logEntry.getTransactionType());
            assertEquals(3, logEntry.getRating().size());
            assertEquals(5, logEntry.getRating().get(0));
            assertEquals(4, logEntry.getRating().get(1));
            assertEquals(3, logEntry.getRating().get(2));

        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

}