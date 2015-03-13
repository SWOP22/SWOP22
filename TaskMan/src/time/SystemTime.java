package time;

import java.time.LocalDateTime;

/**
 * This class has the responsibility to store the current time. It ensures that the current time is
 * not null and that it can never be advanced to a time in the past.
 */
public class SystemTime extends TimeSubject {
    LocalDateTime currentTime;

    /**
     * Constructor for the SystemTime class. The current time argument can not be null.
     * 
     * @param currentTime
     * @throws Exception
     *             if argument == null
     */
    public SystemTime(LocalDateTime currentTime) throws Exception {
	if (currentTime == null) {
	    throw new Exception("The current time can not be null!");
	}
	this.currentTime = currentTime;
    }

    /**
     * @return the current time
     */
    public LocalDateTime getCurrentTime() {
	return currentTime;
    }

    /**
     * Changes the system time to the given time, if the given time is not null and is not in the
     * past.
     * 
     * @param newTime
     * @throws InvalidTimeStampException
     *             if argument == null or argument is before current time
     */
    public void advanceTime(LocalDateTime newTime) throws InvalidTimeStampException {
	if (newTime == null) {
	    throw new InvalidTimeStampException();
	}
	if (newTime.isBefore(currentTime)) {
	    throw new InvalidTimeStampException();
	}
	currentTime = newTime;
	notifyTimeObservers(currentTime);
    }
}