package model;

// Represents an entry in the logbook, Sonny angel name, a date, and the transaction type
public class LogEntry {
    private String angelName; // name of Sonny Angel being logged
    private int date; // date of transaction
    private String transactionType; // type of transaction (traded, sold, added)

    // REQUIRES: angelName is not null or empty, date is a valid date in YYYYMMDD
    // format,
    // transactionType is one of "traded", "sold", or "added"
    // MODIFIES: this
    // EFFECTS: initializes a log entry with the specified angel name, date, and
    // transaction type
    public LogEntry(String angelName, int date, String transactionType) {
        // stub

    }
    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns the angel's name
    public String getAngelName () {
        return null;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns the date of the transaction
    public int getDate() {
        return 0;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: return the transaction type
    public String getTransactionType() {
        return null;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: return true if the transaction type is added
    public boolean isAdded() {
        return false;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: return true if the transaction type is sold or traded
    public boolean isSoldOrTraded() {
        return false;
    }

}
