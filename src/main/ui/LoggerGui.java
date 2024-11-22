package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.LogEntry;
import model.Logbook;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class LoggerGui extends JFrame {
    private Logbook logbook;
    private JTextArea outputArea;
    private JTextField angelNameField;
    private JTextField dateField;
    private JTextField transactionTypeField;
    private JTextField ratingField;

    // MODIFIES: logbook
    // EFFECTS: creates new LoggerGui object and new logbook
    public LoggerGui() {
        logbook = new Logbook();
        setupUI();
    }

    
    // MODIFIES: loggerGUI, loggerGui components
    // EFFECTS: sets up GUI buttons fields and outputs, listens for actions and
    // binds them to methods
    @SuppressWarnings("methodlength")
    private void setupUI() {
        setTitle("Sonny Angel Logger");
        setLayout(new BorderLayout());
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add header panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create and style header label
        JLabel titleLabel = new JLabel("Sonny Angel Logbook");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Create and size the image
        ImageIcon originalIcon = new ImageIcon("./data/image.png"); // Adjust path as needed
        Image image = originalIcon.getImage();
        Image resizedImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedIcon);

        // Add components to header panel
        headerPanel.add(titleLabel);
        headerPanel.add(imageLabel);

        // Add header panel to the NORTH of the frame
        add(headerPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 1)); // Updated to 7 rows for the new button

        JButton addLogEntryButton = new JButton("Add Log Entry");
        JButton viewAngelsButton = new JButton("View Available Angels");
        JButton viewCollectionButton = new JButton("View Collection Count");
        JButton saveButton = new JButton("Save Logbook");
        JButton loadButton = new JButton("Load Logbook");
        JButton rateAngelButton = new JButton("Rate Angel");
        JButton viewAverageRatingButton = new JButton("View Average Rating");
        JButton viewAllEntriesButton = new JButton("View All Entries"); // New button

        buttonPanel.add(addLogEntryButton);
        buttonPanel.add(viewAngelsButton);
        buttonPanel.add(viewCollectionButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(rateAngelButton);
        buttonPanel.add(viewAverageRatingButton);
        buttonPanel.add(viewAllEntriesButton); // Added new button

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        inputPanel.add(new JLabel("Angel Name:"));
        angelNameField = new JTextField();
        inputPanel.add(angelNameField);

        inputPanel.add(new JLabel("Date (YYYYMMDD):"));
        dateField = new JTextField();
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Transaction Type:"));
        transactionTypeField = new JTextField();
        inputPanel.add(transactionTypeField);

        inputPanel.add(new JLabel("Rating (1-5):"));
        ratingField = new JTextField();
        inputPanel.add(ratingField);

        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(buttonPanel, BorderLayout.WEST);
        add(inputPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        addLogEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addLogEntry();
            }
        });

        viewAngelsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAvailableAngels();
            }
        });

        viewCollectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCollectionCount();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveLogbook();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadLogbook();
            }
        });

        rateAngelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rateAngel();
            }
        });

        viewAverageRatingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAverageRating();
            }
        });

        viewAllEntriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllEntries();
            }
        });
    }

    // REQUIRES valid inputs from angelNameField, dateField and transactionTypeField
    // MODIFIES logbook, outputArea
    // EFFECTS adds new log entry to logbook and appends confirmation message to
    // outputArea
    private void addLogEntry() {
        String angelName = angelNameField.getText();
        int date = Integer.parseInt(dateField.getText());
        String transactionType = transactionTypeField.getText();

        LogEntry entry = new LogEntry(angelName, date, transactionType);
        logbook.addLogEntry(entry);

        outputArea.append("Log entry added: " + angelName + " (" + transactionType + ") on " + date + "\n");
    }

    // MODIFIES outputArea
    // EFFECTS displays list of available angels in outputArea, or message if none
    // are available
    private void viewAvailableAngels() {
        List<String> angels = logbook.getAvailableAngels();
        if (angels.isEmpty()) {
            outputArea.append("No Sonny Angels here :(\n");
        } else {
            outputArea.append("Available Angels: \n");
            for (String angel : angels) {
                outputArea.append(angel + "\n");
            }
        }
    }

    // MODIFIES outputArea
    // EFFECTS displays total number of angels in collection
    private void viewCollectionCount() {
        int count = logbook.getCollectionCount();
        outputArea.append("You have " + count + " Sonny Angels in your collection!\n");
    }

    // REQUIRES logbook has entries
    // MODIFIES JSON and outputArea
    // EFFECTS saves logbook to file, appends message outputArea
    private void saveLogbook() {
        JsonWriter writer = new JsonWriter("./data/logbook.json");
        try {
            writer.open();
            writer.writeLogbook(logbook.getAllEntries());
            writer.close();
            outputArea.append("Logbook saved successfully!\n");
        } catch (FileNotFoundException e) {
            outputArea.append("Unable to save logbook: file not found.\n");
        }
    }

    // REQUIRES JSON file
    // MODIFIES logbook, outputArea
    // EFFECTS load entries from logbook, append message to outputArea
    private void loadLogbook() {
        JsonReader reader = new JsonReader("./data/logbook.json");
        try {
            List<LogEntry> logEntries = reader.read();
            for (LogEntry entry : logEntries) {
                logbook.addLogEntry(entry);
            }
            outputArea.append("Logbook loaded successfully!\n");
        } catch (IOException e) {
            outputArea.append("Unable to load logbook: file not found or corrupted.\n");
        }
    }

    // REQUIRES angelNameField, ratingField be valid
    // MODIFIES logbook, outputArea
    // EFFECTS rates angel and appends message to outputArea
    private void rateAngel() {
        String angelName = angelNameField.getText();
        int rating = Integer.parseInt(ratingField.getText());

        logbook.rateAngel(angelName, rating);

        outputArea.append("Rated " + angelName + " with a score of " + rating + "\n");
    }

    // MODIFIES outputArea
    // EFFECTS displays avg rating of all angels in collection in outputArea
    private void viewAverageRating() {
        double averageRating = logbook.getAverageCollectionRating();
        outputArea.append("Average Rating of Angels in Collection: " + averageRating + "\n");
    }

    // MODIFIES: outputArea
    // EFFECTS: displays all log entries in the outputArea, or a message if no entries exist
    private void viewAllEntries() {
        List<LogEntry> entries = logbook.getAllEntries();
        if (entries.isEmpty()) {
            outputArea.append("No entries in the logbook yet!\n");
        } else {
            outputArea.append("All Log Entries:\n");
            for (LogEntry entry : entries) {
                outputArea.append(String.format("Angel: %s, Date: %d, Transaction: %s\n", 
                    entry.getAngelName(), entry.getDate(), entry.getTransactionType()));
            }
            outputArea.append("------------------------\n");
        }
    }

    // EFFECTS creates instance of LoggerGUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoggerGui gui = new LoggerGui();
                gui.setVisible(true);
            }
        });
    }
}