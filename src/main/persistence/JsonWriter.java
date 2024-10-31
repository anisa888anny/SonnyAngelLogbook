package persistence;

import model.LogEntry;
import model.Logbook;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// referenced code given in demo JSON

//represents a writer
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    // cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // writes JSON representation of logbook to file
    public void writeLogbook(List<LogEntry> logEntries) {
        JSONArray jsonArray = new JSONArray();
        for (LogEntry log : logEntries) {
            jsonArray.put(entryToJson(log));
        }

        saveToFile(jsonArray.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES:this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    private JSONObject entryToJson(LogEntry log) {
        JSONObject eventJson = new JSONObject();
        eventJson.put("angelName", log.getAngelName());
        eventJson.put("date", log.getDate());
        eventJson.put("transactionType", log.getTransactionType());

        JSONArray ratingsJson = new JSONArray();
        for (int rating : log.getRating()) {
            ratingsJson.put(rating);
        }
        eventJson.put("ratings", ratingsJson);

        return eventJson;
    }

}