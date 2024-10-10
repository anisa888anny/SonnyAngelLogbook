package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Logbook {
    private List<LogEntry> entries; // list of the log entries recorded

    // EFFECTS: initializes empty list of log entries
    public Logbook() {
        this.entries = new ArrayList<>();

    }

    // REQUIRES: entry has non-null input, and contains
    // name, date, and transaction type, no null fields
    // EFFECTS: adds the entry to the list of entries
    public void addLogEntry(LogEntry entry) {
        entries.add(entry);
    }

    // EFFECTS: returns list of names of Sonny Angels that have been added
    // but not sold or traded
    public List<String> getAvailableAngels() {
        Set<String> addedAngels = new HashSet<>();
        Set<String> removedAngels = new HashSet<>();

        for (LogEntry entry : entries) {
            if (entry.isAdded()) {
                addedAngels.add(entry.getAngelName());
            } else if (entry.isSoldOrTraded()) {
                removedAngels.add(entry.getAngelName());
            }
        }
        addedAngels.removeAll(removedAngels);
        return new ArrayList<>(addedAngels);
    }

    // EFFECTS: returns the number of unique Sonny Angels that have been
    // added but not sold or traded
    public int getCollectionCount() {
        return getAvailableAngels().size();
    }
    
    // EFFECTS: returns all logbook entries 
    public List<LogEntry> getAllEntries() {
        return new ArrayList<>(entries); 
    }
}
