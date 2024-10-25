package model.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.LogEntry;

public class TestLogEntry {

    private LogEntry testEntry1;
    private LogEntry testEntry2;
    private LogEntry testEntry3;

    @BeforeEach
    void runBefore() {
        testEntry1 = new LogEntry("Apple", 20230413, "added");
        testEntry2 = new LogEntry("Cabbage", 20240506, "sold");
        testEntry3 = new LogEntry("Squid", 20140610, "traded");

    }

    @Test
    void testConstructor() {
        assertEquals("Apple", testEntry1.getAngelName());
        assertEquals(20230413, testEntry1.getDate());
        assertEquals("added", testEntry1.getTransactionType());

    }

    @Test
    void testIsAdded() {
        assertTrue(testEntry1.isAdded());
        assertFalse(testEntry2.isAdded());
        assertFalse(testEntry3.isAdded());

    }

    @Test
    void testIsSoldOrTraded() {
        assertTrue(testEntry2.isSoldOrTraded());
        assertTrue(testEntry3.isSoldOrTraded());
        assertFalse(testEntry1.isSoldOrTraded());

    }

    @Test
    void testAddRating() {
        testEntry1.addRating(5);
        testEntry1.addRating(4);

        assertEquals(4.5, testEntry1.getAverageRating());
    }

    @Test
    void testAverageRatingNoRatings() {
        assertEquals(0.0, testEntry1.getAverageRating());
    }

    @Test
    void testAverageRatingWithOneRating() {
        testEntry1.addRating(3);
        assertEquals(3, testEntry1.getAverageRating());
    }

    @Test
    void testGetRating() {
        assertTrue(testEntry1.getRating().isEmpty());
        testEntry1.addRating(3);
        testEntry1.addRating(4);
        testEntry1.addRating(5);
        List<Integer> ratings = testEntry1.getRating();
        assertEquals(3, ratings.size());
        assertEquals(3, ratings.get(0));
        assertEquals(4, ratings.get(1));
        assertEquals(5, ratings.get(2));
    }

    @Test
    void testToJson() {
        testEntry1.addRating(4);
        testEntry1.addRating(5);

        JSONObject json = testEntry1.toJson();

        assertEquals("Apple", json.getString("angelName"));
        assertEquals(20230413, json.getInt("date"));
        assertEquals("added", json.getString("transactionType"));

        assertTrue(json.has("ratings"));
        assertEquals(2, json.getJSONArray("ratings").length());
        assertEquals(4, json.getJSONArray("ratings").getInt(0));
        assertEquals(5, json.getJSONArray("ratings").getInt(1));
    }
}
