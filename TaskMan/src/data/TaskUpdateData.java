package data;

import java.util.Date;

import task.Status;
import task.Task;

public class TaskUpdateData {
	private Task task;
	private Date startTime;
	private Date endTime;
	private Status status;
	
	public TaskUpdateData(Task task) {
		this.task = task;
	}
	
	public Task getTask() {
		return task;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
}
