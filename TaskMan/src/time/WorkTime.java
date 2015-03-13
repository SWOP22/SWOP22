package time;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class WorkTime {
	
	//Checks if the given day is during the weekends or not
	//If the day is not in the weekend then it'll pass to another constructor
	public void WorkWeek(LocalDateTime startDayHour, Integer EstimatedMinutes) {
		DayOfWeek weekDay = startDayHour.getDayOfWeek();
	
		Integer EstimatedHours = (int) Math.floor(EstimatedMinutes / 60);
		EstimatedMinutes = EstimatedMinutes % 60;

		if (weekDay.toString() != "SATURDAY" && weekDay.toString() != "SUNDAY")
		{
			WorkHours(startDayHour, EstimatedHours, EstimatedMinutes);
		}
		else if (weekDay.toString() == "SATURDAY") {
			weekDay.plus(2);
		}
		else {
			weekDay.plus(1);
		}
	}
	
	public LocalDateTime WorkHours(LocalDateTime startDayHour, Integer estimatedHours, Integer estimatedMinutes) {
		int startHour = 8;
		int endHour = 16;
		
		int workingHours = startDayHour.getHour();
		int restTime = (workingHours + estimatedHours) -  endHour;
		
		if (restTime <= 0) {
			return startDayHour.plusHours(estimatedHours);
		}
		else {
			startDayHour.plusDays(1);
			WorkWeek(startDayHour, (restTime*60) + estimatedMinutes);
			return null;
		}
	}
	
	public int getHoursBetween(LocalDateTime begin, LocalDateTime end) {
		
		
		return 0;
	}
}
