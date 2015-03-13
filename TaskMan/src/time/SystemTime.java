package time;

import java.time.LocalDateTime;

public class SystemTime extends TimeSubject {
    LocalDateTime currentTime;

    public SystemTime(LocalDateTime currentTime) throws Exception {
	if (currentTime == null) {
	    throw new Exception("The current time can not be null!");
	}
	this.currentTime = currentTime;
    }

    public LocalDateTime getCurrentTime() {
	return currentTime;
    }
    
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