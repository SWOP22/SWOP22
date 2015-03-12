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
    
    public void advanceTime(long minutes) {
	currentTime.plusMinutes(minutes);
	notifyTimeObservers(currentTime);
    }
}