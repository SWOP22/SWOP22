package data;

import java.time.LocalDateTime;
import task.Status;
import task.Task;

public class TaskUpdateData {
    private Task task;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Status status;

    public TaskUpdateData(Task task) {
	this.task = task;
    }

    public Task getTask() {
	return task;
    }

    public LocalDateTime getStartTime() {
	return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
	this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
	return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
	this.endTime = endTime;
    }

    public Status getStatus() {
	return status;
    }

    public void setStatus(Status status) {
	this.status = status;
    }
}
