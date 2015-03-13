package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import project.Project;
import task.Failed;
import task.Finished;
import task.Ongoing;
import task.Status;
import task.Task;
import user.User;
import data.ProjectData;
import data.TaskData;
import data.TaskUpdateData;
import exceptions.InvalidProjectDataException;


enum TaskStatus { ONGOING, FINISHED, FAILED }

public class TaskManInitFileChecker extends StreamTokenizer {
	
	private FrontController fc;
	
	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	public TaskManInitFileChecker(Reader r, FrontController fc) throws FileNotFoundException{
		super(r);
		this.fc = fc;
	}
	
	public int nextToken() {
		try {
			return super.nextToken();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	void error(String msg) {
		throw new RuntimeException("Line " + lineno() + ": " + msg);
	}
	
	boolean isWord(String word) {
		return ttype == TT_WORD && sval.equals(word);
	}
	
	void expectChar(char c) {
		if (ttype != c)
			error ("'" + c + "' expected");
		nextToken();
	}
		
	void expectLabel(String name) {
		if (!isWord(name))
			error("Keyword '" + name + "' expected");
		nextToken();
		expectChar(':');
	}
	
	String expectStringField(String label) {
		expectLabel(label);
		if (ttype != '"')
			error("String expected");
		String value = sval;
		nextToken();
		return value;
	}
	
	LocalDateTime expectDateField(String label) {
		String date = expectStringField(label);
		return LocalDateTime.parse(date, dateTimeFormatter);
	}
	
	int expectInt() {
		if (ttype != TT_NUMBER || nval != (double)(int)nval)
			error("Integer expected");
		int value = (int)nval;
		nextToken();
		return value;
	}
	
	int expectIntField(String label) {
		expectLabel(label);
		return expectInt();
	}
	
	List<Integer> expectIntList() {
		ArrayList<Integer> list = new ArrayList<>();
		expectChar('[');
		while (ttype == TT_NUMBER){
			list.add(expectInt());
			if (ttype == ',')
				expectChar(',');
			else if (ttype != ']')
				error("']' (end of list) or ',' (new list item) expected");
		}
		expectChar(']');
		return list;
	}
	/**
	 * Get the task you want
	 * System: project ids [0,...] task ids [0,...]
	 * File: project ids [0,...] task ids [1,...]
	 * @param pid
	 * @param tid
	 * @return
	 */
	private Task getTask(int pid, int tid){
		Task task = null;
		for(Project p : fc.getProjects()){
			if(p.getProjectID() == pid){
				for(Task t : p.getAllTasks()){
					if(t.getTaskID() == tid-1){
						task = t;
					}
				}
			}
		}
		return task;
	}
	
	public void checkFile() throws Exception {
		slashSlashComments(false);
		slashStarComments(false);
		ordinaryChar('/'); // otherwise "//" keeps treated as comments.
		commentChar('#');
		
		nextToken();
		expectLabel("projects");
		
		int taskCounter = 1;
		
		while (ttype == '-') {
			expectChar('-');
			String name = expectStringField("name");
			String description = expectStringField("description");
			LocalDateTime creationTime = expectDateField("creationTime");
			LocalDateTime dueTime = expectDateField("dueTime");
			ProjectData pData = fc.getProjectData();
			pData.setName(name);
			pData.setDescription(description);
			pData.setCreationTime(creationTime);
			pData.setDueTime(dueTime);
			fc.createProject(pData);
		}
		int lastProject = -1;
		expectLabel("tasks");
		while (ttype == '-') {
			expectChar('-');
			Project currentProject = null;
			int project = expectIntField("project");
			for(Project p : fc.getProjects()){
				if(p.getProjectID() == project){
					currentProject = p;
				}
			}
			if(project != lastProject){
				taskCounter = 1;
				lastProject = project;
			}
			if(currentProject != null){
				TaskData tData = fc.getTaskData(currentProject);
				String description = expectStringField("description");
				int estimatedDuration = expectIntField("estimatedDuration");
				int acceptableDeviation = expectIntField("acceptableDeviation");
				tData.setDescription(description);
				tData.setEstimatedDuration(estimatedDuration);
				tData.setAcceptableDeviation(acceptableDeviation);
				Integer alternativeFor = null;
				expectLabel("alternativeFor");
				if (ttype == TT_NUMBER){
					alternativeFor = expectInt();
					// Inconsistentie!!! kan geen taak alternate maken voor een taak die nog moet falen.
					//tData.setAlternateFor(getTask(currentProject.getProjectID(),alternativeFor));
				}
				List<Integer> prerequisiteTasks = new ArrayList<>();
				List<Task> preTasks = new ArrayList<Task>();
				expectLabel("prerequisiteTasks");
				if (ttype == '['){
					prerequisiteTasks = expectIntList();
					for(int i : prerequisiteTasks){
						preTasks.add(getTask(currentProject.getProjectID(),i));
					}
					tData.setDependencyTasks(preTasks);
				}
				tData.setUser(new User("Admin"));
				fc.createTask(tData);
				expectLabel("status");
				Status status = new Ongoing();
				if (isWord("finished")) {
					nextToken();
					status = new Finished();
				} else if (isWord("failed")) {
					nextToken();
					// Inconsistentie!!! kan geen taak falen die nog open dependencies heeft
					//status = new Failed();
					LocalDateTime startTime = expectDateField("startTime");
					LocalDateTime endTime = expectDateField("endTime");
				}
				if (!status.ongoing()) {
					LocalDateTime startTime = expectDateField("startTime");
					LocalDateTime endTime = expectDateField("endTime");
					TaskUpdateData tud = fc.getTaskUpdateData(getTask(currentProject.getProjectID(),taskCounter),currentProject);
					tud.setStatus(status);
					tud.setStartTime(startTime);
					tud.setEndTime(endTime);
					fc.taskStatusUpdate(tud);
				}
				taskCounter++;
			} else {
				throw new InvalidProjectDataException("Project ID's don't match.");
			}
		}
		if (ttype != TT_EOF)
			error("End of file or '-' expected");
	}
}