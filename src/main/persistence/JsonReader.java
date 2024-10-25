package persistence;

import model.Logbook;
import model.LogEntry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// referenced code given in demo JSON

// represents a reader 
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;

    }

    public ArrayList<LogEntry> read() throws IOException {
        // String jsonData = readFile(source);
        // JSONObject jsonObject = new JSONObject(jsonData);
        // return parseLogbook(jsonObject);

        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseLogbook(jsonArray);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();

    }

    // EFFECTS: parses logbook from JSON object and returns it
    private ArrayList<LogEntry> parseLogbook(JSONArray jsonArray) {
        ArrayList<LogEntry> logEntries =  new ArrayList<>();

        for (Object json : jsonArray) {
            JSONObject logJson = (JSONObject) json;
            LogEntry logEntry = parseLogEntry(logJson);
            logEntries.add(logEntry);
        }

        return logEntries;

    }

    // // MODIFIES: logbook
    // // EFFECTS: parses logentries from JSON object and adds them to logbook
    // private void addLogEntries(Logbook logbook, JSONObject jsonObject) {
    //     JSONArray jsonArray = jsonObject.getJSONArray("entries");
    //     for (Object json : jsonArray) {
    //         JSONObject nextEntry = (JSONObject) json;
    //         logbook.addLogEntry(parseLogEntry(nextEntry));
    //     }

    // }

    // MODIFIES: logbook
    // EFFECTS: parses logentry from JSON object and adds it to logbook
    private LogEntry parseLogEntry(JSONObject jsonObject) {
        String name = jsonObject.getString("angelName");
        int date = jsonObject.getInt("date");
        String transactionType = jsonObject.getString("transactionType");
        LogEntry entry = new LogEntry(name, date, transactionType);

        JSONArray ratingsArray = jsonObject.getJSONArray("ratings");
        //for (int i = 0; i < ratingsArray.length(); i++) {
      //      entry.addRating(ratingsArray.getInt(i));
       // }


        return entry;

    }

}
