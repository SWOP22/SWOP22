package project;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import task.Ongoing;
import task.Status;
import task.Task;
import time.TimeObserver;
import time.WorkTime;
import data.ProjectData;
import data.TaskData;
import data.TaskUpdateData;
import exceptions.InvalidProjectDataException;

/**
 * The Project class contains all the information about a project.
 * When an instance of a project is created, it automatically receives an ID from the ProjectHandler who counts the amount
 * of project and gives them an ID.
 * The project constructor contains this ID and the ProjectData.
 * The ProjectData is received from the Data Transfer Object Handler which contains a class with project data.
 * The data consists of a project name, description, creation date and due date.
 * When a project is created its status is set to ongoing. The status will not change until all tasks are finished.
 * A project also contains a list of all his tasks. When a task is created the project will save it in the list.
 * 
 * @author SWOP22
 *
 */
public class Project implements TimeObserver {
	
	private int ID;
	private String name;
	private String description;
	private LocalDateTime creationTime;
	private LocalDateTime dueTime;
	private Status status;
	private List<Task> allTasks;
	
	/**
	 * Constructs a Project with an ID and ProjectData.
	 * The ProjectData is name, description, creation date and due date.
	 * @param pID
	 * 				An ID is assigned by the ProjectHandler depending on how much projects exist.
	 * @param pData
	 * 				ProjectData consists of a name, description, creation date and due date.
	 * @throws InvalidProjectDataException
	 * 				Throws an InvalidProjectDataException when the given data is not correct.
	 */
	public Project(int pID, ProjectData pData) throws InvalidProjectDataException {
		String pName = pData.getName();
		String pDescription = pData.getDescription();
		LocalDateTime pCreationTime = pData.getCreationTime();
		LocalDateTime pDueTime = pData.getDueTime();
		
		allFieldChecks(pID, pName, pDescription, pCreationTime, pDueTime);
		
		setProjectID(pID);
		setName(pName);
		setDescription(pDescription);
		setCreationTime(pCreationTime);
		setDueTime(pDueTime);
		this.status = new Ongoing();
		this.allTasks = new ArrayList<Task>();
	}
	
	/**
	 * Returns a string representation of the project.
	 */
	public String toString(){
		//TODO add time info depending on status/delay
		return "Project " + name + ": " + status.getStatus();
	}
	
	/**
	 * Returns the project ID.
	 * @return 
	 * 				the project ID in an int.
	 */
	public int getProjectID() {
		return ID;
	}
	
	/**
	 * Sets the project ID to the given ID.
	 * @param projectID
	 * 				the given ProjectID generated in the ProjectHandler
	 */
	public void setProjectID(int ID) {
		this.ID = ID;
	}
	
	/**
	 * Returns the name of the project.
	 * @return
	 * 				the project name in a String.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the project name to the given name.
	 * @param name
	 * 				the given project name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the description of the project.
	 * @return
	 * 				the project description in a String.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description of the project to the given description.
	 * @param description
	 * 				the given description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Returns the creation date of the project.
	 * @return
	 * 				the creation date of the project in a LocalDateTime.
	 */
	public LocalDateTime getCreationTime() {
		return creationTime;
	}
	
	/**
	 * Sets the creation date of a project to the given date.
	 * @param creationTime
	 * 				the given creation date.
	 */
	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}
	
	/**
	 * Returns the due date of the project.
	 * @return
	 * 				the due date of the project in a LocalDateTime.
	 */
	public LocalDateTime getDueTime() {
		return dueTime;
	}
	
	/**
	 * Sets the due date of a project to the given date.
	 * @param dueTime
	 * 				the given due date.
	 */
	public void setDueTime(LocalDateTime dueTime) {
		this.dueTime = dueTime;
	}
	
	/**
	 * Returns the status of the project.
	 * @return
	 * 				the status of the project in a String.
	 */
	public String getStatus() {
		if(checkFinished())
			return "finished";
		else
			return "ongoing";
	}
	
	/**
	 * Sets the status of the project to finished.
	 * Will run the checkFinished method to check if the status can be finished.
	 */
	public void setFinishedStatus() {
		if(checkFinished())
			finished();
	}
	
	/**
	 * Returns a list of all the tasks of the project.
	 * @return
	 * 				the list of all the tasks of the projects in a List<Task>.
	 */
	public List<Task> getAllTasks() {
		return allTasks;
	}
	
	/**
	 * Returns the ongoing status for the project.
	 * @return
	 * 				the ongoing status of the project in a boolean.
	 */
	public boolean ongoing() {
		return status.ongoing();
    }

	/**
	 * Returns the finished status for the project.
	 * @return
	 * 				the finished status of the project in a boolean.
	 */
    public boolean finished() {
    	return status.finished();
    }

    /**
     * Creates a new Task in the Project and adds it to the allTasks list.
     * @param tData
     * 				the TaskData containing the project, a description, the user, an estimated duration, an acceptable deviation,
     * 				a list of all its dependencies(if there are some) and an alternate task(if there is one).
     * @throws Exception
     * 				throws an Exception if the given TaskData is not correct.
     * @throws NullPointerException 
     * 				throws a NullPointerException is the TaskData is null.
     */
	public void createTask(TaskData tData) throws NullPointerException, Exception {
		Task newTask = new Task(getTaskID(), tData);
		allTasks.add(newTask);
	}
	
	/**
	 * Returns the ID the task will receive, generated from the amount of tasks already in the project.
	 * @return
	 * 				the ID of a task in an int.
	 */
	public int getTaskID() {
		return allTasks.size();
	}
	
	/**
	 * Updates the status of a task from a TaskUpdateData.
	 * @param tUData
	 * 				the TaskUpdateData containing the task that needs to be updated, a new start date, a new end date
	 * 				and a new status.
	 * @throws Exception 
	 */
	public void taskStatusUpdate(TaskUpdateData tUData) throws Exception {
		if(tUData.getTask()!=null){
			boolean isTaskOfProject = false;
			
			for(Task task : allTasks) {
				if(tUData.getTask() == task) {
					isTaskOfProject = true;
				}
			}
			
			if(isTaskOfProject) {
				tUData.getTask().updateTask(tUData);
			}
			else {
				throw new Exception("Can't update a task of another project.");
			}
		} else {
			throw new Exception("Can't update a task that doesn't exist.");
		}
	}
	
	/**
	 * Runs all the field checks.
	 * @param projectID
	 * 				the ID of the project.
	 * @param name
	 * 				the name of the project.
	 * @param description
	 * 				the description of the project.
	 * @param creationTime
	 * 				the creation date of the project.
	 * @param dueTime
	 * 				the due date of the project.
	 * @throws InvalidProjectDataException
	 * 				throws an InvalidProjectDataException if the given data is not correct for a certain field.
	 */
	public void allFieldChecks(int ID, String name, String description, LocalDateTime creationTime, LocalDateTime dueTime) throws InvalidProjectDataException {
		checkID(ID);
		checkName(name);
		checkDescription(description);
		checkCreationTime(creationTime);
		checkDueTime(dueTime);
		checkBothDates(creationTime, dueTime);
	}
	
	public void checkID(int ID) throws InvalidProjectDataException {
		if(ID < 0)
			throw new InvalidProjectDataException("The ID cannot be negative.");
	}
	
	/**
	 * Checks if the given name is not empty nor null.
	 * @param name
	 * 				the name for the project.
	 * @throws InvalidProjectDataException
	 * 				throws an InvalidProjectDataException if the given name is not correct.
	 */
	public void checkName(String name) throws InvalidProjectDataException {
		if(name.equals("") || name == null)
			throw new InvalidProjectDataException("The name field cannot be empty.");
	}
	
	/**
	 * Checks if the given description is not empty nor null.
	 * @param description
	 * 				the description for the project.
	 * @throws InvalidProjectDataException
	 * 				throws an InvalidProjectDataException if the given description is not correct.
	 */
	public void checkDescription(String description) throws InvalidProjectDataException {
		if(description.equals("") || description == null)
			throw new InvalidProjectDataException("The description field cannot be empty.");
	}
	
	/**
	 * Checks if the given creation date is not null.
	 * @param creationTime
	 * 				the creation date for the project.
	 * @throws InvalidProjectDataException
	 * 				throws an InvalidProjectDataException if the given creation date is not correct.
	 */
	public void checkCreationTime(LocalDateTime creationTime) throws InvalidProjectDataException {
		if(creationTime == null)
			throw new InvalidProjectDataException("The creation date cannot be empty.");
	}
	
	/**
	 * Checks if the given due date is not null.
	 * @param dueTime
	 * 				the due date for the project.
	 * @throws InvalidProjectDataException
	 * 				throws an InvalidProjectDataException if the given due date is not correct.
	 */
	public void checkDueTime(LocalDateTime dueTime) throws InvalidProjectDataException {
		if(dueTime == null)
			throw new InvalidProjectDataException("The due date cannot be empty.");
	}
	
	/**
	 * Checks if the given due date is not before the creation date.
	 * @param creationTime
	 * 				the creation date for the project.
	 * @param dueTime
	 * 				the due date for the project.
	 * @throws InvalidProjectDataException
	 * 				throws an InvalidProjectDataException if the given due date is not correct.
	 */
	public void checkBothDates(LocalDateTime creationTime, LocalDateTime dueTime) throws InvalidProjectDataException {
		if(creationTime.isAfter(dueTime))
			throw new InvalidProjectDataException("The due date cannot be before the creation date.");
	}
	
	public void checkAllTasks(Task task) throws InvalidProjectDataException {
		if(task == null)
			throw new InvalidProjectDataException("The task added to the list cannot be null.");
	}
	
	public long checkDelay() {
		int totalEstimatedDuration = 0;
		long delay = 0;
		LocalDateTime maxEnd = null;
		LocalDateTime end = null;
		LocalDateTime totalTasksTime = null;
		
		for(Task task : allTasks) {
			if(task.getTimeSpan().getStartTime() != null) {
				if(!task.ongoing())
					end = task.getTimeSpan().getEndTime();
				else
					end = task.getEstimatedEndTime();
				
				if(maxEnd != null) {
					if(end.compareTo(maxEnd) > 0)
						maxEnd = end;
				}
				else {
					maxEnd = end;
				}
			}
			else {
				totalEstimatedDuration += task.getEstimatedDuration();
			}
		}
		
		totalTasksTime = maxEnd;
		
		if(totalEstimatedDuration != 0) 
			totalTasksTime = getEndDate(maxEnd, totalEstimatedDuration);
		
		if(totalTasksTime.compareTo(dueTime) > 0)
			delay = dueTime.until( totalTasksTime, ChronoUnit.HOURS);
		
		return delay;
	}
	
	public LocalDateTime getEndDate(LocalDateTime startDate, int addHours) {
		return WorkTime.getEstimatedEndTime(startDate, addHours);
	}
	
	/**
	 * Checks if the status of a project can be set to finished.
	 * The check will first look in the allTasks list if there are any tasks, if not return false.
	 * If there are tasks, it will check if all tasks are set to finished, or if the failed tasks have a finished alternative.
	 * If any of the tasks is not finished the isFinished boolean value will be set to false and this will be returned at the end.
	 * @return
	 * 				isFinished is a boolean set as default to true. If any task is not finished it will be changed to false.
	 */
	public boolean checkFinished() {
    	boolean isFinished = true;
    	
    	if (!allTasks.isEmpty()) {
			for (Task task : allTasks) {
				if (task.ongoing())
					isFinished = false;
				else if (task.failed())
					isFinished = checkAlternativeStatus(task);
			}
		}
    	else {
    		isFinished = false;
    	}
    	
		return isFinished;
    }
	
	/**
	 * Checks if the alternate task of a task has a finished status.
	 * It will first check if the task has an alternative, if not return false.
	 * If it has an alternative, it will check if the status of the alternative is set to finished.
	 * If the status is ongoing, it will return false.
	 * If the status is failed, it will check if the alternative task has an alternative task and check his status.
	 * @param task
	 * 				the task of which alternative task must be checked.
	 * @return
	 * 				returns the value of isFinished. The value will be set to false if the task is not finished.
	 */
	public boolean checkAlternativeStatus(Task task) {
		boolean isFinished = true;
		boolean alternateFound = false;
		
		for(Task alternateTask : allTasks) {
			if(alternateTask.getAlternateFor() == task) {
				if (alternateTask.ongoing()) {
					isFinished = false;
				}
				else if (alternateTask.failed()) {
					isFinished = checkAlternativeStatus(alternateTask);
				}
				
				alternateFound = true;
			}
		}
		
		if(!alternateFound) {
			isFinished = false;
		}
		
		return isFinished;
	}
    
	/**
	 * Checks whether a task is available or not.
	 * It will first check if the project contains any tasks, if not return false.
	 * If it has tasks, it will check all the dependencies of all tasks.
	 * If the dependencies of a task are not finished, it will return false.
	 * If the dependencies of a task is failed, it will check his alternative and run the checkAlternativeStatus method.
	 * @param task
	 * @return
	 */
    public boolean checkTaskAvailability(Task task) {
    	boolean response = true;
    	
    	if(!allTasks.contains(task)) {
    		return false;
    	}
    	else {
    		for(Task dependencyTask : task.getDependencyTasks()) {
        		if(dependencyTask.ongoing()) {
        			response = false;
        		}
        		else if(dependencyTask.failed()) {
        			response = checkAlternativeStatus(dependencyTask);
        		}
        	}
    	}
    	
    	return response;
    }
    
    /**
     * Updates the project whenever the TimeStamp is changed.
     */
    @Override
    public void update(LocalDateTime currentTime) {
    	checkDelay();
    }
}
