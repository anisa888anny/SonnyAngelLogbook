package model.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Event;
import model.LogEntry;

public class TestEvent {
    private Event e;
    private Date d;
    private LogEntry l;

    @BeforeEach
    public void runBefore() {
        l = new LogEntry("bob", 20200101, "added");
        e = new Event("New log entry with " + l.getAngelName() + " added");
        d = Calendar.getInstance().getTime();
    }

    @Test
    public void testEvent() {
        assertEquals("New log entry with " + l.getAngelName() + " added", e.getDescription());
        assertEquals(d, e.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "New log entry with " + l.getAngelName() + " added", e.toString());
    }

}
