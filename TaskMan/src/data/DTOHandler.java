package data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import project.Project;
import task.Task;

public class DTOHandler {

	public ProjectData getProjectData() {
		return new ProjectData();
	}

	public TaskData getTaskData(Project project) {
		return new TaskData(project);
	}

	public TaskUpdateData getTaskUpdateData(Task task) {
		return new TaskUpdateData(task);
	}
	
	public Date DateTimeConverter(String stringDate) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = format.parse(stringDate);
		
		return date;
	}

}
