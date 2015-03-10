package main;

import java.util.List;

import project.Project;
import project.ProjectHandler;
import time.TimeHandler;
import time.TimeStamp;
import data.InvalidProjectDataException;
import data.ProjectData;
import data.TaskData;
import data.TaskUpdateData;

public class TaskManager {
	
	private ProjectHandler pH;
	private TimeHandler tH;
	
	public TaskManager() {
		pH = new ProjectHandler();
		tH = new TimeHandler();
	}
	
	public List<Project> getProjects() {
		return pH.getProjects();
	}
	
	public void createProject(ProjectData pData) throws InvalidProjectDataException {
		pH.createProject(pData);
	}

	public void createTask(TaskData tData) {
		pH.createTask(tData);
	}

	public void taskStatusUpdate(TaskUpdateData tUData) {
		pH.taskStatusUpdate(tUData);
	}

	public void advanceTime(TimeStamp time) {
		tH.advanceTime(time);
	}

}
