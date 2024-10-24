package persistence;

import model.Logbook;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// referenced code given in demo JSON

public class JsonWriter {

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {

    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    // cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
    }

    // MODIFIES: this
    // writes JSON representation of logbook to file
    public void write(Logbook logbook) {

    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {

    }

    // MODIFIES:this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {

    }

}
