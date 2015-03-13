package time;

import java.time.LocalDateTime;

/**
 * The WorkTime class provides a method to calculate the estimated end time of a task
 */
public class WorkTime {
    // starting hour of a work day
    private static int startHour = 8;
    // ending hour of a work day
    private static int endHour = 16;

    /**
     * @param startTime
     * @param estimatedDuration
     * @return null if startTime == null, estimated end time otherwise
     */
    public static LocalDateTime getEstimatedEndTime(LocalDateTime startTime, int estimatedDuration) {
	if (startTime == null) {
	    return null;
	}
	LocalDateTime estimatedEndTime = startTime;

	for (int i = 0; i < estimatedDuration; i++) {
	    if (estimatedEndTime.getDayOfWeek().name().toUpperCase() == "SATURDAY"
		    || estimatedEndTime.getDayOfWeek().name().toUpperCase() == "SUNDAY") {
		while (!(estimatedEndTime.getDayOfWeek().name().toUpperCase() == "MONDAY" && estimatedEndTime
			.getHour() == startHour)) {
		    estimatedEndTime = estimatedEndTime.plusDays(1);
		    estimatedEndTime = estimatedEndTime.withHour(startHour);
		}
	    }
	    if (estimatedEndTime.getHour() < startHour) {
		estimatedEndTime = estimatedEndTime.withHour(startHour);
	    }
	    if (estimatedEndTime.getHour() > (endHour - 1)) {
		estimatedEndTime = estimatedEndTime.plusDays(1);
		estimatedEndTime = estimatedEndTime.withHour(startHour);
	    }
	    estimatedEndTime = estimatedEndTime.plusMinutes(1);
	}

	return estimatedEndTime;
    }
}
