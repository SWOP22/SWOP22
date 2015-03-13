package task;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;

public class TimeSpanTest {

    @Test
    public void test() {
	TimeSpan timeSpan = null;
	LocalDateTime startTime = LocalDateTime.of(2015, 1, 1, 12, 0);
	LocalDateTime endTime = LocalDateTime.of(2015, 1, 1, 11, 0);

	// Test constructor
	// endTime before startTime
	try {
	    timeSpan = new TimeSpan(startTime, endTime);
	    fail("TimeSpan with end time before start time accepted!");
	} catch (Exception e) {
	}

	try {
	    timeSpan = new TimeSpan(null, null);
	} catch (Exception e) {
	    fail("Could not create TimeSpan with valid arguments!");
	}

	try {
	    timeSpan = new TimeSpan(startTime, null);
	} catch (Exception e) {
	    fail("Could not create TimeSpan with valid arguments!");
	}

	try {
	    timeSpan = new TimeSpan(null, endTime);
	} catch (Exception e) {
	    fail("Could not create TimeSpan with valid arguments!");
	}

	// startTime == endTime
	endTime = LocalDateTime.of(2015, 1, 1, 12, 0);
	try {
	    timeSpan = new TimeSpan(startTime, endTime);
	} catch (Exception e) {
	    fail("Could not create TimeSpan with valid arguments!");
	}

	// startTime < endTime
	endTime = LocalDateTime.of(2015, 1, 1, 13, 0);
	try {
	    timeSpan = new TimeSpan(startTime, endTime);
	} catch (Exception e) {
	    fail("Could not create TimeSpan with valid arguments!");
	}

	// Test getters
	if (timeSpan != null) {
	    assertEquals(startTime, timeSpan.getStartTime());
	    assertEquals(endTime, timeSpan.getEndTime());
	}

	// Test setters
	startTime = LocalDateTime.of(2015, 1, 1, 14, 0);
	// startTime > timeSpan.getEndTime()
	try {
	    timeSpan.setStartTime(startTime);
	    fail("Start time after end time!");
	} catch (Exception e) {
	}
	// timeSpan.getStartTime() > endTime
	endTime = LocalDateTime.of(2015, 1, 1, 11, 0);
	try {
	    timeSpan.setEndTime(endTime);
	    fail("Start time after end time!");
	} catch (Exception e) {
	}
	// startTime < timeSpan.getEndTime()
	startTime = LocalDateTime.of(2015, 1, 1, 12, 0);
	try {
	    timeSpan.setStartTime(startTime);
	} catch (Exception e) {
	    fail("Did not accept a valid argument!");
	}
	// timeSpan.getStartTime() < endTime
	endTime = LocalDateTime.of(2015, 1, 1, 13, 0);
	try {
	    timeSpan.setEndTime(endTime);
	} catch (Exception e) {
	    fail("Did not accept valid argument!");
	}
	// startTime == null, timeSpan.getEndTime() != null
	try {
	    timeSpan.setStartTime(null);
	} catch (Exception e) {
	    fail("Did not accept null argument!");
	}
	// timeSpan.getStartTime() == null, endTime != null
	try {
	    timeSpan.setEndTime(endTime);
	} catch (Exception e) {
	    fail("Did not accept valid argument!");
	}
	// timeSpan.getStartTime() == null, endTime == null
	try {
	    timeSpan.setEndTime(null);
	} catch (Exception e) {
	    fail("Did not accept null argument!");
	}
	// startTime != null, timeSpan.getEndTime() == null
	try {
	    timeSpan.setStartTime(startTime);
	} catch (Exception e) {
	    fail("Did not accept a valid argument!");
	}
	// timeSpan.getStartTime() != null, endTime == null
	try {
	    timeSpan.setEndTime(null);
	} catch (Exception e) {
	    fail("Did not accept null argument!");
	}
	// startTime == null, timeSpan.getEndTime() == null
	try {
	    timeSpan.setStartTime(null);
	} catch (Exception e) {
	    fail("Did not accept null argument!");
	}
    }

}
