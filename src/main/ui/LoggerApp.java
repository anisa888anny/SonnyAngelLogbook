package ui;

import java.util.Scanner;
import model.LogEntry;
import model.Logbook;
import java.util.List;

public class LoggerApp {
    private Logbook logbook;
    private Scanner scanner;

    public LoggerApp() {
        logbook = new Logbook();
        scanner = new Scanner(System.in);

    }

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
        System.out.println("5. Exit?");
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
}
