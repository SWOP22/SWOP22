package task;

import java.time.LocalDateTime;

/**
 * TimeSpan class to represent a start and end time. This class guarantees that an end time can
 * never be before a start time, but they can be null.
 */
public class TimeSpan {
    private LocalDateTime startTime, endTime;

    public TimeSpan(LocalDateTime startTime, LocalDateTime endTime) throws Exception {
	if (startTime != null && endTime != null) {
	    if (endTime.isBefore(startTime)) {
		throw new Exception("The end time can never be before the start time!");
	    }
	}
	this.startTime = startTime;
	this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
	return this.startTime;
    }

    public void setStartTime(LocalDateTime startTime) throws Exception {
	if (startTime != null && endTime != null) {
	    if (endTime.isBefore(startTime)) {
		throw new Exception("The end time can never be before the start time!");
	    }
	}
	this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
	return this.endTime;
    }

    public void setEndTime(LocalDateTime endTime) throws Exception {
	if (startTime != null && endTime != null) {
	    if (endTime.isBefore(startTime)) {
		throw new Exception("The end time can never be before the start time!");
	    }
	}
	this.endTime = endTime;
    }
}