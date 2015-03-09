package task;

import java.util.Collections;
import java.util.List;

/*
 * TODO: more documentation, unit tests for consistency checks
 */

public class Task {
    private String description;
    private User user;
    private int estimatedDuration; // in minutes
    private double acceptableDeviation; // percentage
    // contains the tasks that need to be completed before this task can start
    private List<Task> dependencyTasks;
    // the alternateTask can replace this task to complete the project
    private Task alternateTask;
    private TimeSpan timeSpan;
    private Status status;

    public Task(String description, User user, int estimatedDuration, double acceptableDeviation,
	    List<Task> dependencyTasks, Task alternateTask, TimeSpan timeSpan, Status status)
	    throws Exception {
	checkDescription(description);
	checkUser(user);
	checkDuration(estimatedDuration);
	checkDeviation(acceptableDeviation);
	checkDependencyTasks(dependencyTasks);
	checkAlternateTask(alternateTask);
	this.description = description;
	this.user = user;
	this.estimatedDuration = estimatedDuration;
	this.acceptableDeviation = acceptableDeviation;
	this.dependencyTasks = dependencyTasks;
	this.alternateTask = alternateTask;
	this.timeSpan = timeSpan;
	this.status = status;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) throws Exception {
	checkDescription(description);
	this.description = description;
    }

    public String getUserName() {
	return user.getName();
    }

    public void setUser(User user) throws Exception {
	checkUser(user);
	this.user = user;
    }

    public int getEstimatedDuration() {
	return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) throws Exception {
	checkDuration(estimatedDuration);
	this.estimatedDuration = estimatedDuration;
    }

    public double getAcceptableDeviation() {
	return acceptableDeviation;
    }

    public void setAcceptableDeviation(double acceptableDeviation) throws Exception {
	checkDeviation(acceptableDeviation);
	this.acceptableDeviation = acceptableDeviation;
    }

    public List<Task> getDependencyTasks() {
	// returns a read-only list
	return Collections.unmodifiableList(dependencyTasks);
    }

    // !!!
    // A project needs to check that all its tasks' dependencies relate to tasks of
    // the same project, tasks do not have the information to check this themselves.
    // !!!
    public void setDependencyTasks(List<Task> dependencyTasks) throws Exception {
	checkDependencyTasks(dependencyTasks);
	this.dependencyTasks = dependencyTasks;
    }

    // !!!
    // A project needs to check that all its tasks' dependencies relate to tasks of
    // the same project, tasks do not have the information to check this themselves.
    // !!!
    public void addDependencyTask(Task dependencyTask) throws Exception {
	checkDependencyTask(dependencyTask);
	this.dependencyTasks.add(dependencyTask);
    }

    public Task getAlternateTask() {
	return alternateTask;
    }

    // !!!
    // A project needs to check that its tasks' alternatives relate to tasks of
    // the same project, tasks do not have the information to check this themselves.
    // !!!
    public void setAlternateTask(Task alternateTask) throws Exception {
	checkAlternateTask(alternateTask);
	this.alternateTask = alternateTask;
    }

    public TimeSpan getTimeSpan() {
	return timeSpan;
    }

    public void setTimeSpan(TimeSpan timeSpan) throws Exception {
	checkTimeSpan(timeSpan);
	this.timeSpan = timeSpan;
    }

    public Status getStatus() {
	return status;
    }

    public void setStatus(Status status) throws Exception {
	checkStatus(status);
	this.status = status;
    }

    // Private methods for consistency checking

    private void checkDescription(String description) throws Exception {
	if (description.equals("")) {
	    throw new Exception("Task description can not be empty!");
	}
    }

    private void checkUser(User user) throws Exception {
	if (user == null) {
	    throw new Exception("A task must belong to a user!");
	}
    }

    private void checkDuration(int duration) throws Exception {
	if (duration < 0) {
	    throw new Exception("The expected duration can not be negative!");
	}
    }

    private void checkDeviation(double acceptableDeviation) throws Exception {
	if (acceptableDeviation < 0) {
	    throw new Exception("The acceptable deviation can not be negative!");
	}
    }

    private void checkDependencyTasks(List<Task> dependencyTasks) throws Exception {
	for (Task task : dependencyTasks) {
	    if (this == task) {
		throw new Exception("A task can not depend on itself!");
	    }
	}
    }

    private void checkDependencyTask(Task dependencyTask) throws Exception {
	if (this == dependencyTask) {
	    throw new Exception("A task can not depend on itself!");
	}
    }

    private void checkAlternateTask(Task alternateTask) throws Exception {
	if (this == alternateTask) {
	    throw new Exception("A task can not be an alternative for itself!");
	}
    }

    private void checkTimeSpan(TimeSpan timeSpan) throws Exception {
	// The time span can only be changed if the task is currently ongoing
	if (!status.ongoing()) {
	    throw new Exception("The time span of finished and failed tasks can not be changed!");
	}
	// Check valid time span
	if (timeSpan.getStartTime() <= timeSpan.getEndTime()) {
	    throw new Exception("The start time has to be before or equal to the end time!");
	}
    }

    private void checkStatus(Status status) throws Exception {
	// The status of finished and failed tasks can not be changed
	if (!this.status.ongoing()) {
	    throw new Exception("The status of finished and failed tasks can not be changed!");
	}
	// The status can only be set to finished, if the time span is set
	if (status.finished() && this.timeSpan == null) {
	    throw new Exception("A task can only be finished if it has a time span!");
	}
	// If the status is set to failed and the time span isn't set, it will be set to (0,0)
	if (!status.finished() && !status.ongoing() && this.timeSpan == null) {
	    this.timeSpan = new TimeSpan(0, 0);
	}
    }
}