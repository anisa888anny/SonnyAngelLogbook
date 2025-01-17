package model.persistence;

import model.LogEntry;
import model.Logbook;
import persistence.JsonReader;
import persistence.JsonWriter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestJsonWriter extends TestJson {

    @Test
    void testWriterInvalidFile() {
        try {
            Logbook logbook = new Logbook();
            JsonWriter writer = new JsonWriter("./data/\0illegal:fileName.json");
            writer.open();
            fail("FileNotFoundException was expected");
        } catch (FileNotFoundException e) {
            // pass: expected exception was thrown
        }
    }

    @Test
    void testWriterEmptyLogbook() {
        try {
            Logbook logbook = new Logbook();
            List<LogEntry> logEntries = logbook.getAllEntries();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLogbook.json");
            writer.open();
            writer.writeLogbook(logEntries);
            writer.close();

            String jsonData = Files.readString(Paths.get("./data/testWriterEmptyLogbook.json"));
            assertFalse(jsonData.contains("\"entries\":[]"));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralLogbook() {
        try {
            Logbook logbook = new Logbook();
            logbook.addLogEntry(new LogEntry("Angel1", 20230101, "added"));
            logbook.addLogEntry(new LogEntry("Angel2", 20230102, "sold"));

            List<LogEntry> logEntries = logbook.getAllEntries();

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLogbook.json");
            writer.open();
            writer.writeLogbook(logEntries);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralLogbook.json");
            List<LogEntry> entries = reader.read();
            assertEquals("Angel1", entries.get(0).getAngelName());
            assertEquals("added", entries.get(0).getTransactionType());
            assertEquals("Angel2", entries.get(1).getAngelName());
            assertEquals("sold", entries.get(1).getTransactionType());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testEntryToJson() {
        try {
            LogEntry logEntry = new LogEntry("TestAngel", 20231015, "added");
            logEntry.addRating(5);
            logEntry.addRating(4);
            logEntry.addRating(3);

            JsonWriter writer = new JsonWriter("./data/dummy.json");
            Method method = JsonWriter.class.getDeclaredMethod("entryToJson", LogEntry.class);
            method.setAccessible(true); // Make the method accessible.

            JSONObject jsonObject = (JSONObject) method.invoke(writer, logEntry);

            assertEquals("TestAngel", jsonObject.getString("angelName"));
            assertEquals(20231015, jsonObject.getInt("date"));
            assertEquals("added", jsonObject.getString("transactionType"));

            JSONArray ratingsArray = jsonObject.getJSONArray("ratings");
            assertEquals(3, ratingsArray.length());
            assertEquals(5, ratingsArray.getInt(0));
            assertEquals(4, ratingsArray.getInt(1));
            assertEquals(3, ratingsArray.getInt(2));

        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

}
