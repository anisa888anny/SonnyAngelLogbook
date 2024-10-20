package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// creates a new logbook that stores log entries
public class Logbook {
    private List<LogEntry> entries; // list of the log entries recorded

    // EFFECTS: initializes empty list of log entries
    public Logbook() {
        this.entries = new ArrayList<>();

    }

    // REQUIRES: entry has non-null input, and contains
    // name, date, and transaction type, no null fields
    // MODIFIES: this
    // EFFECTS: adds the entry to the list of entries
    public void addLogEntry(LogEntry entry) {
        entries.add(entry);
    }

    // MODIFIES: this   
    // EFFECTS: returns list of names of Sonny Angels that have been added
    // but not sold or traded
    public List<String> getAvailableAngels() {
        Set<String> availableAngels = new HashSet<>();
        //Set<String> removedAngels = new HashSet<>();

        for (LogEntry entry : entries) {
            if (entry.isAdded()) {
                availableAngels.add(entry.getAngelName());
            } else  {
                availableAngels.remove(entry.getAngelName());
            }
        }
        //addedAngels.removeAll(removedAngels);
        return new ArrayList<>(availableAngels);
    }

    // MODIFIES: this
    // EFFECTS: returns the number of unique Sonny Angels that have been
    // added but not sold or traded
    public int getCollectionCount() {
        return getAvailableAngels().size();
    }

    // EFFECTS: returns all logbook entries
    public List<LogEntry> getAllEntries() {
        return new ArrayList<>(entries);
    }

    // REQUIRES: angelName is in the list of entries and it's rating
    // is between 1 and 5
    // MODIFIES: this
    // EFFECTS: adds a rating for the specified angel
    public void rateAngel(String angelName, int rating) {
        for (LogEntry entry : entries) {
            if (entry.getAngelName().equals(angelName)) {
                entry.addRating(rating);
                return;
            }
        }
        System.out.println("Angel not found in the collection.");
    }

    // MODIFIES: this
    // EFFECTS: returns average rating of all angels in collection
    public double getAverageCollectionRating() {
        if (entries.isEmpty()) {
            return 0.0; // No entries, return 0
        }
        double totalRating = 0.0;
        int ratedAngels = 0;

        for (LogEntry entry : entries) {
            double avgRating = entry.getAverageRating();
            if (avgRating > 0) {
                totalRating += avgRating;
                ratedAngels++;
            }
        }
        return ratedAngels > 0 ? totalRating / ratedAngels : 0.0;
    }
}
