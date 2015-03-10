package project;

import java.util.Date;
import java.util.List;

import data.InvalidProjectDataException;
import data.ProjectData;
import data.TaskData;
import data.TaskUpdateData;
import task.Ongoing;
import task.Status;
import task.Task;


public class Project {
	private String name;
	private String description;
	private Date creationTime;
	private Date dueTime;
	private Status status;
	private List<Task> allTasks;
	
	
	public Project(ProjectData pData) throws InvalidProjectDataException {
		String pName = pData.getName();
		String pDescription = pData.getDescription();
		Date pCreationTime = pData.getCreationTime();
		Date pDueTime = pData.getDueTime();
		
		this.name = pName;
		this.description = pDescription;
		this.creationTime = pCreationTime;
		this.dueTime = pDueTime;
		this.status = new Ongoing();
		this.allTasks = null;
	}
	
	
	//Getters and Setters
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
	
	public Date getCreationTime() {
		return creationTime;
	}
	
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	
	public Date getDueTime() {
		return dueTime;
	}
	
	public void setDueTime(Date dueTime) {
		this.dueTime = dueTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Task> getAllTasks() {
		return allTasks;
	}

	public void setAllTasks(List<Task> allTasks) {
		this.allTasks = allTasks;
	}
	
	public boolean ongoing() {
		return status.ongoing();
    }

    public boolean finished() {
    	return status.finished();
    }

	public void createTask(TaskData tData) {
		Task newTask = new Task(tData);
		allTasks.add(newTask);
	}

	public void taskStatusUpdate(TaskUpdateData tUData) {
		Task.setStatus(tUData);
	}
	
}
