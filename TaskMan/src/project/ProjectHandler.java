package project;

import java.util.List;

import data.InvalidProjectDataException;
import data.ProjectData;
import data.TaskData;
import data.TaskUpdateData;

public class ProjectHandler {
	private Project project;
	
	public void createProject(ProjectData pData) throws InvalidProjectDataException {
		project = new Project(pData);
	}
	
	public List<Project> getProjects() {
		List<Project> allProjects = null;
		
		return allProjects;
	}
	
	public void createTask(TaskData tData) {
		project.createTask(tData);
	}
	
	public void taskStatusUpdate(TaskUpdateData tUData) {
		project.taskStatusUpdate(tUData);
	}
}
