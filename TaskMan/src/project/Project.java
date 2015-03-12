package project;

import java.time.LocalDateTime;
import java.util.List;

import data.ProjectData;
import data.TaskData;
import data.TaskUpdateData;
import exceptions.InvalidProjectDataException;
import task.Ongoing;
import task.Status;
import task.Task;
import time.TimeObserver;

/**
 * 
 * @author SWOP22
 *
 */
public class Project implements TimeObserver {
	
	private int projectID;
	private String name;
	private String description;
	private LocalDateTime creationTime;
	private LocalDateTime dueTime;
	private Status status;
	private List<Task> allTasks;
	
	
	public Project(int projectID, ProjectData pData) throws InvalidProjectDataException {
		String pName = pData.getName();
		String pDescription = pData.getDescription();
		LocalDateTime pCreationTime = pData.getCreationTime();
		LocalDateTime pDueTime = pData.getDueTime();
		
		allFieldChecks(pName, pDescription, pCreationTime, pDueTime);
		
		setProjectID(projectID);
		setName(pName);
		setDescription(pDescription);
		setCreationTime(pCreationTime);
		setDueTime(pDueTime);
		this.status = new Ongoing();
		this.allTasks = null;
	}

	//Getters and Setters
	public int getProjectID() {
		return projectID;
	}
	
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public LocalDateTime getCreationTime() {
		return creationTime;
	}
	
	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}
	
	public LocalDateTime getDueTime() {
		return dueTime;
	}
	
	public void setDueTime(LocalDateTime dueTime) {
		this.dueTime = dueTime;
	}

	public String getStatus() {
		if(checkFinished())
			return "finished";
		else
			return "ongoing";
	}
	
	public void setFinishedStatus() {
		if(checkFinished())
			finished();
	}

	public List<Task> getAllTasks() {
		return allTasks;
	}
	
	public boolean ongoing() {
		return status.ongoing();
    }

    public boolean finished() {
    	return status.finished();
    }

	public void createTask(TaskData tData) {
		/*Task newTask = new Task(tData);
		allTasks.add(newTask);*/
	}
	
	public int getTaskID() {
		return allTasks.size() - 1;
	}

	public void taskStatusUpdate(TaskUpdateData tUData) {
		//Task.setStatus(tUData);
	}
	
	//Checks
	public void allFieldChecks(String name, String description, LocalDateTime creationTime, LocalDateTime dueTime) throws InvalidProjectDataException {
		checkName(name);
		checkDescription(description);
		checkCreationTime(creationTime);
		checkDueTime(dueTime);
		checkBothDates(creationTime, dueTime);
	}
	
	public void checkName(String name) throws InvalidProjectDataException {
		if(name.equals(""))
			throw new InvalidProjectDataException();
	}

	public void checkDescription(String description) throws InvalidProjectDataException {
		if(description.equals(""))
			throw new InvalidProjectDataException();
	}
	
	public void checkCreationTime(LocalDateTime creationTime) throws InvalidProjectDataException {
		if(creationTime == null)
			throw new InvalidProjectDataException();
	}
	
	public void checkDueTime(LocalDateTime dueTime) throws InvalidProjectDataException {
		if(dueTime == null)
			throw new InvalidProjectDataException();
	}
	
	public void checkBothDates(LocalDateTime creationTime, LocalDateTime dueTime) throws InvalidProjectDataException {
		if(creationTime.compareTo(dueTime) < 0)
			throw new InvalidProjectDataException();
	}
	/*
	public int checkDelay() {
		int totalEstimatedDuration = 0;
		int delay = 0;
		LocalDateTime maxEnd = null;
		LocalDateTime end = null;
		LocalDateTime totalTasksTime = null;
		
		for(Task task : allTasks) {
			if(task.getTimeSpan().getStartTime() != null) {
				if(!task.ongoing())
					end = task.getTimeSpan().getEndTime();
				else
					end = task.getEstimatedEndTime();
				
				if(end.compareTo(maxEnd) > 0)
					maxEnd = end;
			}
			else {
				totalEstimatedDuration += task.getEstimatedDuration();
			}
		}
		
		if(totalEstimatedDuration != 0)
			totalTasksTime = getEndDate(maxEnd, totalEstimatedDuration);
		
		if(totalTasksTime.compareTo(dueTime) > 0)
			delay = getHoursBetweenDates(dueTime, totalTasksTime);
		
		return delay;
	}
	
	public LocalDateTime getEndDate(LocalDateTime startDate, int addHours) {
		return WorkWeek.getEndTime(startDate, addHours);
	}
	
	public int getHoursBetween(LocalDateTime startDate, LocalDateTime endDate) {
		return WorkWeek.getHoursBetween(startDate, endDate);
	}
	*/
	public boolean checkFinished() {
    	boolean isFinished = true;
    	
    	for(Task task : allTasks) {
			if(task.ongoing())
				isFinished = false;
			else if (!task.finished())
				isFinished = false;
		}
    	
    	return isFinished;
    }
    
    public boolean checkTaskAvailability(Task task) {
    	boolean response = true;
    	
    	if(!allTasks.contains(task)) {
    		return false;
    	}
    	else {
    		for(Task dependencyTask : task.getDependencyTasks()) {
        		if(!dependencyTask.finished()) {
        			if(dependencyTask.getAlternateTask() == null) {
        				response = false;
        			}
        			else {
        				Task alternateTask = dependencyTask.getAlternateTask();
        				
        				if(!alternateTask.finished())
        					response = false;
        			}
        		}
        	}
    	}
    	
    	return response;
    }

    @Override
    public void update(LocalDateTime currentTime) {
	// TODO Auto-generated method stub
	
    }
}
