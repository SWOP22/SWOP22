package data;

import java.util.List;

import project.Project;
import task.Task;
import task.User;

public class TaskData {
    // the the project that will own this task
    private Project project;
    private String description;
    private User user;
    private int estimatedDuration; // in minutes
    private int acceptableDeviation; // percentage
    // contains the tasks that need to be completed before this task can start
    private List<Task> dependencyTasks;
    // the alternateFor can replace a task to complete the project
    private Task alternateFor;

    public TaskData(Project project) {
	this.project = project;
    }

    public Project getProject() {
	return project;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public int getEstimatedDuration() {
	return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
	this.estimatedDuration = estimatedDuration;
    }

    public int getAcceptableDeviation() {
	return acceptableDeviation;
    }

    public void setAcceptableDeviation(int acceptableDeviation) {
	this.acceptableDeviation = acceptableDeviation;
    }

    public List<Task> getDependencyTasks() {
	return dependencyTasks;
    }

    public void setDependencyTasks(List<Task> dependencyTasks) {
	this.dependencyTasks = dependencyTasks;
    }

    public void addDependencyTask(Task dependencyTask) {
	this.dependencyTasks.add(dependencyTask);
    }

    public Task getAlternateFor() {
	return alternateFor;
    }

    public void setAlternateFor(Task alternateFor) {
	this.alternateFor = alternateFor;
    }
}
