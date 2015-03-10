package data;

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

}
