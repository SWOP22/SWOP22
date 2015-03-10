package project;

import java.util.Date;
import java.util.List;

import task.*;

public class Project {
	private String name;
	private String description;
	private Date creationTime;
	private Date dueTime;
	private Status status;
	private List<Task> allTasks;
	
	
	public Project(String name, String description, Date creationTime,
			Date dueTime, Status status, List<Task> allTasks) {
		this.name = name;
		this.description = description;
		this.creationTime = creationTime;
		this.dueTime = dueTime;
		this.status = status;
		this.allTasks = allTasks;
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
	
	//Field Checks
	public void checkName(String name) throws Exception {
		if(name.equals(""))
			throw new Exception("A name cannot be empty !");
	}
	
	public void checkDescription(String description) throws Exception {
		if(description.equals(""))
			throw new Exception("A description cannot be empty !");
	}
	
	public void checkCreationTime(Date creationTime) throws Exception {
		if(creationTime == null)
			throw new Exception("A project must have a creation time !");
	}
	
	public void checkDueTime(Date dueTime) throws Exception {
		if(dueTime == null)
			throw new Exception("A project must have a due time !");
	}
}
