package main;

import java.time.LocalDateTime;
import java.util.List;

import project.Project;
import project.ProjectHandler;
import time.SystemTime;
import user.User;
import user.UserManager;
import data.ProjectData;
import data.TaskData;
import data.TaskUpdateData;
import exceptions.InvalidProjectDataException;
import exceptions.InvalidTimeStampException;

public class TaskManager {

    private ProjectHandler pH;
    private SystemTime systemTime;
    private UserManager userManager;

    public TaskManager(LocalDateTime currentTime) throws Exception {
	if (currentTime == null) {
	    throw new Exception("Can not create a TaskManager without a LocalDateTime object!");
	}
	pH = new ProjectHandler();
	systemTime = new SystemTime(currentTime);
	userManager = new UserManager();
    }

    public List<Project> getProjects() {
	return pH.getProjects();
    }

    public void createProject(ProjectData pData) throws InvalidProjectDataException {
	if (pData.getCreationTime().isBefore(systemTime.getCurrentTime())) {
	    throw new InvalidProjectDataException("Can not create projects in the past!");
	}
	pH.createProject(pData);
    }

    public void createTask(TaskData tData) throws NullPointerException, Exception {
	pH.createTask(tData);
    }

    public void taskStatusUpdate(TaskUpdateData tUData) throws Exception {
	pH.taskStatusUpdate(tUData);
    }

    public void advanceTime(LocalDateTime time) throws InvalidTimeStampException {
	systemTime.advanceTime(time);
    }

    public List<User> getUsers() {
	return userManager.getUsers();

    }

    public void addUser(User user) throws Exception {
	userManager.addUser(user);

    }

    public LocalDateTime getTime() {
	return systemTime.getCurrentTime();
    }

}
