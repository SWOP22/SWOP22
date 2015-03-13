package project;

import java.util.ArrayList;
import java.util.List;

import time.TimeSubject;
import data.ProjectData;
import data.TaskData;
import data.TaskUpdateData;
import exceptions.InvalidProjectDataException;
import exceptions.InvalidTaskDataException;

public class ProjectHandler {
	private List<Project> allProjects;
	
	public ProjectHandler() {
		allProjects = new ArrayList<Project>();
	}
	
	public void createProject(ProjectData pData, TimeSubject timeSubject) throws InvalidProjectDataException {
		checkExistingName(pData.getName());
		Project project = new Project(getProjectID(), pData);
		allProjects.add(project);
		try {
		    timeSubject.addTimeObserver(project);
		} catch (Exception e) {
		    throw new InvalidProjectDataException("Could not add project to list of time observers");
		}
	}
	
	public List<Project> getProjects() {
		return allProjects;
	}
	
	public int getProjectID() throws InvalidProjectDataException {
		int projectID = allProjects.size();
		checkSameID(projectID);
		return projectID;
	}
	
	public void createTask(TaskData tData) throws NullPointerException, Exception {
		if(tData.getProject()!= null){
			tData.getProject().createTask(tData);
		} else {
			throw new Exception("Can't create tasks on projects that don't exist.");
		}
	}
	
	public void taskStatusUpdate(TaskUpdateData tUData) throws Exception {
		if(tUData.getTask()!=null){
			tUData.getTask().updateTask(tUData);
		} else {
			throw new Exception("Can't update a task that doesn't exist.");
		}
	}
	
	//Checks
	public void checkSameID(int projectID) throws InvalidProjectDataException {
		for(Project project : allProjects) {
			if(projectID == project.getProjectID())
				throw new InvalidProjectDataException("Two projects cannot have the same ID.");
		}
	}
	
	public void checkExistingName(String name) throws InvalidProjectDataException {
		for(Project project : allProjects) {
			if(name.equals(project.getName()))
				throw new InvalidProjectDataException("Two projects cannot have the same name.");
		}
	}
}
