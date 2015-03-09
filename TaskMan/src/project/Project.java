package project;

import java.util.Date;

public class Project {
	private String name;
	private String description;
	private Date creationTime;
	private Date dueTime;
	
	public Project(String name, String description, Date creationTime, Date dueTime) {
		super();
		this.name = name;
		this.description = description;
		this.creationTime = creationTime;
		this.dueTime = dueTime;
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
}
