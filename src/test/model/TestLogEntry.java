package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        testEntry1.addRating (5);
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

}
