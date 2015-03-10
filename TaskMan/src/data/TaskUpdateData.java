package data;

import java.util.Date;

import task.Status;

public class TaskUpdateData {
	private Date startTime;
	private Date endTime;
	private Status status;
	
	public Date getStartTime(){
		return startTime;
	}
	
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	
	public Date getEndTime(){
		return endTime;
	}
	
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	
	public Status getStatus(){
		return status;
	}
	
	public void setStatus(Status status){
		this.status = status;
	}
}
