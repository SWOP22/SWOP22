package project;

import java.util.Date;
import java.util.List;

import task.*;

public class ProjectHandler {
	public static void createProject(String name, String description, Date creationTime, Date dueTime, Status status, List<Task> allTasks) {
		new Project(name, description, creationTime, dueTime, status, allTasks);
	}
	
	public List<Project> getProjects() {
		List<Project> allProjects = null;
		
		return allProjects;
	}
}
