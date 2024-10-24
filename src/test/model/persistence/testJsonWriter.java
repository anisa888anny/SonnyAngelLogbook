package model.persistence;

import model.LogEntry;
import model.Logbook;
import persistence.JsonWriter;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLogbook.json");
            writer.open();
            writer.write(logbook);
            writer.close();

            String jsonData = Files.readString(Paths.get("./data/testWriterEmptyLogbook.json"));
            assertTrue(jsonData.contains("\"entries\":[]"));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralLogbook() {
        try {
            Logbook logbook = new Logbook();
            logbook.addLogEntry(new LogEntry("Angel1", 20230101, "added"));
            logbook.addLogEntry(new LogEntry("Angel2", 20230102, "removed"));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLogbook.json");
            writer.open();
            writer.write(logbook);
            writer.close();

            String jsonData = Files.readString(Paths.get("./data/testWriterGeneralLogbook.json"));
            assertTrue(jsonData.contains("\"angelName\":\"Angel1\""));
            assertTrue(jsonData.contains("\"transactionType\":\"added\""));
            assertTrue(jsonData.contains("\"angelName\":\"Angel2\""));
            assertTrue(jsonData.contains("\"transactionType\":\"removed\""));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
