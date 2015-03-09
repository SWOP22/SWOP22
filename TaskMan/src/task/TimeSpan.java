package task;

public class TimeSpan {
    private int startTime, endTime; // in minutes

    public TimeSpan(int startTime, int endTime) {
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