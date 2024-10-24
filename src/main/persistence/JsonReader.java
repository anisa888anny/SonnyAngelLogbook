package persistence;

import model.Logbook;
import model.LogEntry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// referenced code given in demo JSON

// represents a reader 
public class JsonReader {

    // EFFECTS: constructs reader to read from source file
    JsonReader(String source) {

    }

    Logbook read() throws IOException {
        return null;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return null;
    }

    // EFFECTS: parses logbook from JSON object and returns it
    private Logbook parseLogbook(JSONObject jsonObject) {
        return null;
    }

    // MODIFIES: logbook
    // EFFECTS: parses logentries from JSON object and adds them to logbook
    private void addLogEntries(Logbook logbook, JSONObject jsonObject) {

    }

    // MODIFIES: logbook
    // EFFECTS: parses logentry from JSON object and adds it to logbook
    private LogEntry parseLogEntry(JSONObject jsonObject) {
        return null;
    }

}
