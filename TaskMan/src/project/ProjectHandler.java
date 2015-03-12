package project;

import java.time.LocalDateTime;
import java.util.List;

import data.InvalidProjectDataException;
import data.ProjectData;
import data.TaskData;
import data.TaskUpdateData;

public class ProjectHandler {
	private Project project;
	private List<Project> allProjects;
	
	public void createProject(ProjectData pData) throws InvalidProjectDataException {
		checkExistingName(pData.getName());
		project = new Project(getProjectID(), pData);
		allProjects.add(project);
	}
	
	public List<Project> getProjects() {
		return allProjects;
	}
	
	public int getProjectID() {
		return allProjects.size() - 1;
	}
	
	public void createTask(TaskData tData) {
		project.createTask(tData);
	}
	
	public void taskStatusUpdate(TaskUpdateData tUData) {
		project.taskStatusUpdate(tUData);
	}
	
	//Checks
	public void checkExistingName(String name) throws InvalidProjectDataException {
		for(Project project : allProjects) {
			if(name.equals(project.getName()))
				throw new InvalidProjectDataException();
		}
	}
}
