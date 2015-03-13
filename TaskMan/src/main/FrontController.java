package main;

import java.time.LocalDateTime;
import java.util.List;

import data.DTOHandler;
import data.ProjectData;
import data.TaskData;
import data.TaskUpdateData;
import exceptions.InvalidProjectDataException;
import exceptions.InvalidTaskDataException;
import exceptions.InvalidTaskUpdateDataException;
import project.Project;
import task.Task;
import time.InvalidTimeStampException;
import user.User;

/**
 * The FrontController is a FacadeController for the TaskManager system.
 * It provides the public methods to interact with the TaskManager system.
 * @author Swop22
 *
 */
public class FrontController {
	
	private TaskManager taskMan;
	private DTOHandler dataHandler;
	
	/**
	 * Create a new FrontController for the system.
	 * @throws Exception 
	 */
	public FrontController(LocalDateTime currentTime) throws Exception{
		taskMan = new TaskManager(currentTime);
		dataHandler = new DTOHandler();
	}
	
	/**
	 * Returns a list of all the projects managed by the system.
	 * @return a list of all the projects managed by the system.
	 */
	public List<Project> getProjects(){
		return taskMan.getProjects();
	}
	
	/**
	 * Create a new Project with the given ProjectData.
	 * @param pData the ProjectData to create a new Project.
	 * @throws InvalidProjectDataException thrown when the given ProjectData is not valid.
	 */
	public void createProject(ProjectData pData) throws InvalidProjectDataException {
		taskMan.createProject(pData);
	}
	
	/**
	 * Create a new Task with the given TaskData.
	 * @param tData the TaskData to create a new Task.
	 * @throws Exception 
	 * @throws NullPointerException 
	 */
	public void createTask(TaskData tData) throws NullPointerException, Exception {
		taskMan.createTask(tData);
	}
	
	/**
	 * Update the Status of a Task with the data specified in the TaskUpdateData.
	 * @param tUData the TaskUpdateData with which an update is to be made.
	 * @throws Exception 
	 */
	public void taskStatusUpdate(TaskUpdateData tUData) throws Exception {
		taskMan.taskStatusUpdate(tUData);
	}
	
	/**
	 * Advance the time of the system to the given TimeStamp.
	 * @param time the TimeStamp to advance the system to.
	 * @throws InvalidTimeStampException thrown when the given TimeStamp is not valid.
	 */
	public void advanceTime(LocalDateTime time) throws InvalidTimeStampException {
		taskMan.advanceTime(time);
	}
	
	public ProjectData getProjectData(){
		return dataHandler.getProjectData();
	}
	
	public TaskData getTaskData(Project project){
		return dataHandler.getTaskData(project);
	}
	
	public TaskUpdateData getTaskUpdateData(Task task, Project project){
		return dataHandler.getTaskUpdateData(task,project);
	}
	
	public List<User> getUsers() {
	    return taskMan.getUsers();
	}
	
	public void addUser(User user) throws Exception {
	    taskMan.addUser(user);
	}
}
