package time;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class WorkTime {
	
	private int startHour = 8;
	private int endHour = 16;
	
	//Checks if the given day is during the weekends or not
	//If the day is not in the weekend then it'll pass to another constructor
	public LocalDateTime getEstimatedEndTime(LocalDateTime startDayHour, Integer EstimatedMinutes) {
		LocalDateTime estimatedEndTime = null;
		
		DayOfWeek weekDay = startDayHour.getDayOfWeek();
	
		Integer EstimatedHours = (int) Math.floor(EstimatedMinutes / 60);
		EstimatedMinutes = EstimatedMinutes % 60;

		if (weekDay.toString() != "SATURDAY" && weekDay.toString() != "SUNDAY")
		{
			estimatedEndTime = WorkHours(startDayHour, EstimatedHours, EstimatedMinutes);
		}
		else if (weekDay.toString() == "SATURDAY") {
			weekDay.plus(2);
		}
		else {
			weekDay.plus(1);
		}
		return estimatedEndTime;
	}
	
	public LocalDateTime WorkHours(LocalDateTime startDayHour, Integer estimatedHours, Integer estimatedMinutes) {
		int workingHours = startDayHour.getHour();
		int restTime = (workingHours + estimatedHours) -  endHour;
		
		if (restTime <= 0) {
			return startDayHour.plusHours(estimatedHours).plusMinutes(estimatedMinutes);
		}
		else {
			startDayHour.plusDays(1);
			getEstimatedEndTime(startDayHour, (restTime*60) + estimatedMinutes);
			return null;
		}
	}
	
//	public int getHoursBetween(LocalDateTime begin, LocalDateTime end) {
//		DayOfWeek beginDay = begin.getDayOfWeek();
//		DayOfWeek endDay = end.getDayOfWeek();
//		int hours = 0;
//		
//		if (begin.getHour() >= getEndHour()) {
//			beginDay.plus(1);
//		}
//		
//		if (beginDay.toString() != "SATURDAY" && beginDay.toString() != "SUNDAY")
//		{
//			hours += 8;
//		}
//		else if (weekDay.toString() == "SATURDAY") {
//			weekDay.plus(2);
//		}
//		else {
//			weekDay.plus(1);
//		}
//		
//		return hours;
//	}
	
	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}
}
