package data;

import java.util.List;

import project.Project;
import task.Task;
import task.User;

public class TaskData {
	// the project where you want to create the task with this data
	private Project project;
	private String description;
    private User user;
    private int estimatedDuration; // in minutes
    private double acceptableDeviation; // percentage
    // contains the tasks that need to be completed before this task can start
    private List<Task> dependencyTasks;
    // the alternateTask can replace this task to complete the project
    private Task alternateTask;
    
    public TaskData(Project project) {
    	this.project = project;
    }
    
    public Project getProject() {
    	return project;
    }
    
    public String getDescription() {
    	return description;
    }

    public void setDescription(String description) throws Exception {
    	this.description = description;
    }

    public String getUserName() {
    	return user.getName();
    }

    public void setUser(User user) throws Exception {
    	this.user = user;
    }

    public int getEstimatedDuration() {
    	return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) throws Exception {
    	this.estimatedDuration = estimatedDuration;
    }

    public double getAcceptableDeviation() {
    	return acceptableDeviation;
    }

    public void setAcceptableDeviation(double acceptableDeviation) throws Exception {
    	this.acceptableDeviation = acceptableDeviation;
    }

    public List<Task> getDependencyTasks() {
    	return dependencyTasks;
    }

    public void setDependencyTasks(List<Task> dependencyTasks) throws Exception {
    	this.dependencyTasks = dependencyTasks;
    }

    public void addDependencyTask(Task dependencyTask) throws Exception {
    	this.dependencyTasks.add(dependencyTask);
    }

    public Task getAlternateTask() {
    	return alternateTask;
    }
    
    public void setAlternateTask(Task alternateTask) throws Exception {
    	this.alternateTask = alternateTask;
    }
}
