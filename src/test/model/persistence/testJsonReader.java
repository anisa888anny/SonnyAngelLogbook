package model.persistence;


import model.LogEntry;
import model.Logbook;
import persistence.JsonReader;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonReader {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Logbook logbook = reader.read();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass: expected exception was thrown
        }
    }

    @Test
    void testReaderEmptyLogbook() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLogbook.json");
        try {
            Logbook logbook = reader.read();
            assertEquals(0, logbook.getAllEntries().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralLogbook() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralLogbook.json");
        try {
            Logbook logbook = reader.read();
            assertEquals(2, logbook.getAllEntries().size());
            LogEntry entry1 = logbook.getAllEntries().get(0);
            assertEquals("Angel1", entry1.getAngelName());
            assertEquals(20230101, entry1.getDate());
            assertEquals("added", entry1.getTransactionType());

            LogEntry entry2 = logbook.getAllEntries().get(1);
            assertEquals("Angel2", entry2.getAngelName());
            assertEquals(20230102, entry2.getDate());
            assertEquals("removed", entry2.getTransactionType());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
