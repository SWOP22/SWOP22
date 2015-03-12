package data;

import java.time.LocalDateTime;


public class ProjectData {
	
	private String name;
	private String description;
	private LocalDateTime creationTime;
	private LocalDateTime dueTime;
	
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
}
