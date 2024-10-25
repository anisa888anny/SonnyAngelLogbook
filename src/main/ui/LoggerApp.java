package ui;

import java.util.Scanner;
import model.LogEntry;
import model.Logbook;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class LoggerApp {
    private Logbook logbook;
    private ArrayList<LogEntry> logEntries;
    private Scanner scanner;

    public LoggerApp() {
        logbook = new Logbook();
        scanner = new Scanner(System.in);
        logEntries = new ArrayList<>();
    }

    @SuppressWarnings("methodlength")
    // EFFECTS runs the console application
    public void run() {
        boolean keepRunning = true;
        while (keepRunning) {
            displayMenu();
            String command = scanner.nextLine();

            switch (command) {
                case "1":
                    addLogEntry();
                    break;
                case "2":
                    viewAvailableAngels();
                    break;
                case "3":
                    viewCollectionCount();
                    break;
                case "4":
                    viewLogEntries();
                    break;
                case "5":
                    rateAngel();
                    break;
                case "6":
                    viewAverageRating();
                    break;
                case "7":
                    saveLogbook(); // Save the logbook to a file
                    break;
                case "8":
                    loadLogbook(); // Load the logbook from a file
                    break;
                case "9":
                    keepRunning = false;
                    System.out.println("Exiting!");
                    break;
                default:
                    System.out.println("Invalid, try again!");
                    break;
            }
        }
    }

    // EFFECTS: displays the main menu and its options
    private void displayMenu() {
        System.out.println("Welcome to the Sonny Angel Logger!");
        System.out.println("1. Add new log entry?");
        System.out.println("2. View current Sonny Angel collection?");
        System.out.println("3. Check how many Sonny Angels are in collection?");
        System.out.println("4. View all log entries?");
        System.out.println("5. Rate a Sonny Angel?");
        System.out.println("6. View average ratings given?");
        System.out.println("7. Save logbook?");
        System.out.println("8. Load logbook?");
        System.out.println("9. Exit?");
        System.out.println("Please choose a command: ");
    }

    // EFFECTS asks user to enter details for new log entry then adds to logbook
    private void addLogEntry() {
        System.out.println("Enter the name of your Sonny Angel: ");
        String name = scanner.nextLine();

        System.out.println("Enter the date of your transaction (YYYYMMDD): ");
        int date = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter the transaction type (added, sold or traded): ");
        String transactionType = scanner.nextLine();

        LogEntry entry = new LogEntry(name, date, transactionType);
        logbook.addLogEntry(entry);
        System.out.println("Log entry added successfully!");

    }

    // EFFECTS shows list of Sonny Angels if there are any
    private void viewAvailableAngels() {
        List<String> angels = logbook.getAvailableAngels();
        if (angels.isEmpty()) {
            System.out.println("No Sonny Angels here :(");
        } else {
            System.out.println("Available angels: ");
            for (String angel : angels) {
                System.out.println(angel);
            }
        }
    }

    // EFFECTS shows all log entries.
    public void viewLogEntries() {
        List<LogEntry> entries = logbook.getAllEntries();
        if (entries.isEmpty()) {
            System.out.println("No log entries found.");
        } else {
            System.out.println("Logbook Entries:");
            for (LogEntry entry : entries) {
                System.out.printf("Angel: %s, Date: %d, Transaction Type: %s%n",
                        entry.getAngelName(), entry.getDate(), entry.getTransactionType());
            }
        }
    }

    // EFFECTS displays the number of collected sonny angels
    private void viewCollectionCount() {
        int count = logbook.getCollectionCount();
        System.out.println("You have " + count + " Sonny Angels in your collection!");
    }

    // REQUIRES that input angelName match an existing angel's name in logbook
    // rating must be 1-5
    // MODIFIES the corresponding LogEntry for the angel and the rating list is
    // updated
    // EFFECTS asks user to rate an angel, prints confirmation of rating being added
    private void rateAngel() {
        System.out.print("Enter the name of the angel to rate: ");
        String angelName = scanner.nextLine();
        System.out.print("Enter your rating (1-5): ");
        int rating = Integer.parseInt(scanner.nextLine());
        logbook.rateAngel(angelName, rating);
        System.out.println("Rating added for " + angelName);
    }

    // EFFECTS prints average rating
    private void viewAverageRating() {
        double averageRating = logbook.getAverageCollectionRating();
        System.out.println("Average rating of your collection: " + averageRating);
    }

    // MODIFIES: this
    // EFFECTS: saves the logbook data to a JSON file
    private void saveLogbook() {
        JsonWriter writer = new JsonWriter("./data/logbook.json");
        try {
            writer.open();
            writer.writeLogbook(logbook.getAllEntries());
            writer.close();
            System.out.println("Logbook saved successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save logbook: file not found.");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the logbook data from a JSON file
    private void loadLogbook() {
        JsonReader reader = new JsonReader("./data/logbook.json");
        try {
            logEntries = reader.read();
            for (LogEntry entry : logEntries) {
                logbook.addLogEntry(entry);
            }
            System.out.println("Logbook loaded successfully!");
        } catch (IOException e) {
            System.out.println("Unable to load logbook: file not found or corrupted.");
        }
    }

}
