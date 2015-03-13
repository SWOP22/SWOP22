package time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;

import org.junit.Test;

public class SystemTimeTest {

    private class TimeObserverTestClass implements TimeObserver {
	private LocalDateTime currentTime = null;

	public LocalDateTime getCurrentTime() {
	    return currentTime;
	}

	@Override
	public void update(LocalDateTime currentTime) {
	    this.currentTime = currentTime;
	}
    }

    @Test
    public void test() {
	// Initialize the system time
	SystemTime systemTime = null;

	// try to initialize with null object
	try {
	    systemTime = new SystemTime(null);
	    fail("Allowed a null object!");
	} catch (Exception e) {
	}

	try {
	    systemTime = new SystemTime(LocalDateTime.of(2015, 1, 1, 8, 0));
	} catch (Exception e) {
	    fail("Could not initialize the system time!");
	}

	assertNotEquals(null, systemTime);

	// try to add null time observer
	try {
	    systemTime.addTimeObserver(null);
	    fail("Could add a null time observer!");
	} catch (Exception e) {
	}

	// add time observer
	TimeObserverTestClass observer = new TimeObserverTestClass();

	try {
	    systemTime.addTimeObserver(observer);
	} catch (Exception e) {
	    fail("Could not add valid observer!");
	}

	// advancing the system time should update the time observer
	LocalDateTime newTime = LocalDateTime.of(2015, 1, 1, 9, 0);

	try {
	    systemTime.advanceTime(newTime);
	} catch (Exception e) {
	    fail("Could not advance time!");
	}

	assertEquals(newTime, observer.getCurrentTime());

	// remove time observer
	systemTime.removeTimeObserver(observer);

	// if removed succesfully, the time of the observer will not be updated
	LocalDateTime newTime2 = LocalDateTime.of(2015, 1, 1, 10, 0);

	try {
	    systemTime.advanceTime(newTime2);
	} catch (Exception e) {
	    fail("Could not advance time!");
	}

	assertEquals(newTime, observer.getCurrentTime());

	// test system time get method
	assertEquals(newTime2, systemTime.getCurrentTime());

	// try to advance time with null object
	try {
	    systemTime.advanceTime(null);
	    fail("Allowed a null object!");
	} catch (Exception e) {
	}

	// try to advance time with a time that is before the current system time
	try {
	    systemTime.advanceTime(LocalDateTime.of(2015, 1, 1, 8, 0));
	    fail("Allowed a time in the past!");
	} catch (Exception e) {
	}
    }

}
