package model;

import java.util.List;

public class Logbook {
    private List<LogEntry> entries; // list of the log entries recorded

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: initializes empty list of log entries
    public Logbook() {

    }

    // REQUIRES: entry has name, date, and transaction type, no null fields
    // MODIFIES:
    // EFFECTS: adds the entry to the list of entries
    public void addLogEntry(LogEntry entry) {
    
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns list of names of Sonny Angels that have been added 
    // but not sold or traded
    public List<String> getAvailableAngels() {
        return null;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns the number of unique Sonny Angels that have been
    // added but not sold or traded
    public int getCollectionCount() {
        return 0;
    }

}
