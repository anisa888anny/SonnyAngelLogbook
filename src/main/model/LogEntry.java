package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

// Represents an entry in the logbook, Sonny angel name, a date, and the transaction type
public class LogEntry implements persistence.Writable {
    private String angelName; // name of Sonny Angel being logged
    private int date; // date of transaction
    private String transactionType; // type of transaction (traded, sold, added)
    private List<Integer> ratings;

    // REQUIRES: angelName is not null or empty, date is a valid date in YYYYMMDD
    // format, transactionType is one of "traded", "sold", or "added"
    // EFFECTS: initializes a log entry with the specified angel name, date, and
    // transaction type
    public LogEntry(String angelName, int date, String transactionType) {
        this.angelName = angelName;
        this.date = date;
        this.transactionType = transactionType;
        this.ratings = new ArrayList<>();

    }

    // EFFECTS: returns the angel's name
    public String getAngelName() {
        return angelName;
    }

    // EFFECTS: returns the date of the transaction
    public int getDate() {
        return date;
    }

    // EFFECTS: return the transaction type
    public String getTransactionType() {
        return transactionType;
    }

    // EFFECTS: return true if the transaction type is added
    public boolean isAdded() {
        return transactionType.equals("added");
    }

    // EFFECTS: return true if the transaction type is sold or traded
    public boolean isSoldOrTraded() {
        return transactionType.equals("sold") || transactionType.equals("traded");

    }

    // EFFECTS: adds a rating to the list of ratings
    public void addRating(int rating) {
        ratings.add(rating);
    }

    // EFFECTS: adds a rating to the list of ratings
    public List<Integer> getRating() {
        return ratings;
    }

    // EFFECTS: returns average rating of the angel
    public double getAverageRating() {
        if (ratings.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (int rating : ratings) {
            sum += rating;
        }
        return (double) sum / ratings.size();
    }

    // Converts this log entry to a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("angelName", angelName);
        json.put("date", date);
        json.put("transactionType", transactionType);
        json.put("ratings", ratings);
        return json;
    }

}
