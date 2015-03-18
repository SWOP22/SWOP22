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

	public TaskUpdateData getTaskUpdateData(Task task,Project project) {
		return new TaskUpdateData(task,project);
	}

}
