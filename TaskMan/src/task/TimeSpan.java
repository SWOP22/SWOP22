package task;

// currently works with integers as a temporary solution
/**
 * TimeSpan class to represent a start and an end time
 */
public class TimeSpan {
    private int startTime, endTime; // in minutes

    public TimeSpan(int startTime, int endTime) throws Exception {
	if (startTime > endTime) {
	    throw new Exception("The start time can never be after the end time!");
	}
	this.startTime = startTime;
	this.endTime = endTime;
    }

    public int getStartTime() {
	return this.startTime;
    }

    public int getEndTime() {
	return this.endTime;
    }
}