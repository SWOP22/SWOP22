package time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;

import org.junit.Test;

public class SystemTimeTest {
    @Test
    public void test() {
	// Initialize the system time
	SystemTime systemTime = null;

	// try to initialize with null object
	try {
	    systemTime = new SystemTime(null);
	    fail("The SystemTime constructor allowed a null argument!");
	} catch (Exception e) {
	}

	// try to initialize with a valid argument
	LocalDateTime time = LocalDateTime.of(2015, 1, 1, 10, 0);

	try {
	    systemTime = new SystemTime(time);
	} catch (Exception e) {
	    fail("Could not initialize the system time!");
	}

	assertNotEquals(null, systemTime);
	assertEquals(time, systemTime.getCurrentTime());

	// try to advance time with a time that is before the current system time
	try {
	    systemTime.advanceTime(LocalDateTime.of(2015, 1, 1, 8, 0));
	    fail("Allowed a time in the past!");
	} catch (Exception e) {
	}

	// try to advance time with a null object
	try {
	    systemTime.advanceTime(null);
	    fail("The advance time method allowed a null argument!");
	} catch (Exception e) {
	}

	// try to advance the time with a valid argument
	time = LocalDateTime.of(2015, 1, 1, 12, 0);
	
	try {
	    systemTime.advanceTime(time);
	} catch (Exception e) {
	    fail("Could not initialize the system time!");
	}
	
	assertEquals(time, systemTime.getCurrentTime());
    }

}
