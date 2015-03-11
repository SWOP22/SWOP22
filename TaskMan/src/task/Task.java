package task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Task class to represent the task objects as described in the assignment.
 * 
 * To guarantee consistency, finished and failed tasks can not be modified! Therefore, you should
 * update all the relevant information before changing the status to finished or failed. To prevent
 * undesirable scenarios, we added exceptions to alert the user if certain conditions aren't met.
 * 
 * Because we wanted the minimize the coupling between the Project and the Task classes, the
 * association between them is unidirectional. Tasks have no information about the projects that own
 * them. Therefore, they can not guarantee that their dependencies and alternates relate to tasks of
 * the same project. To solve this problem without increasing the coupling between the 2 classes and
 * following the Information Expert principle, a project must guarantee that it's tasks can only
 * depend on, or be an alternate for each other.
 */
public class Task {
    private int taskID;
    private String description;
    private User user;
    private int estimatedDuration; // in minutes
    private int acceptableDeviation; // represents a percentage
    private List<Task> dependencyTasks;
    private Task alternateTask;
    private TimeSpan timeSpan;
    private Status status;

    /**
     * The constructor for the Task class. To simplify the consistency checking, the status of a new
     * task is always "ongoing" and the time span is always set to null.
     * 
     * @param taskID
     *            identifier for this task, ID's start from 0
     * @param description
     *            the task description, can not be null or empty
     * @param user
     *            the user this task belongs to, can not be null
     * @param estimatedDuration
     *            the estimated time (in minutes) needed to complete the task, can not be negative
     * @param acceptableDeviation
     *            a percentage that determines whether the task is finished on time, can not be
     *            negative
     * @param dependencyTasks
     *            a list that contains the tasks that need to be completed before this task can be
     *            finished, can not contain null values and can not cause a loops
     * @param alternateTask
     *            the alternate task can replace this task to complete the project, can be null, can
     *            not cause a loop
     * @throws Exception
     *             if the given arguments are not valid, an exception will be thrown
     */
    public Task(int taskID, String description, User user, int estimatedDuration,
	    int acceptableDeviation, List<Task> dependencyTasks, Task alternateTask)
	    throws Exception {
	// New tasks don't have a time span and have the status ongoing
	this.timeSpan = null;
	this.status = new Ongoing();

	// Check all arguments
	checkTaskID(taskID);
	checkDescription(description);
	checkUser(user);
	checkDuration(estimatedDuration);
	checkDeviation(acceptableDeviation);
	if (dependencyTasks == null) {
	    dependencyTasks = new ArrayList<Task>();
	} else {
	    checkDependencyTasks(dependencyTasks);
	}
	checkAlternateTask(alternateTask);

	// After all arguments are checked, assign values
	this.taskID = taskID;
	this.description = description;
	this.user = user;
	this.estimatedDuration = estimatedDuration;
	this.acceptableDeviation = acceptableDeviation;
	this.dependencyTasks = dependencyTasks;
	this.alternateTask = alternateTask;
    }

    /**
     * @return the identifier of the task
     */
    public int getTaskID() {
	return taskID;
    }

    /**
     * @return the task description, not null, not empty
     */
    public String getDescription() {
	return description;
    }

    /**
     * Sets the task description to the given String. The description can not be null or empty and
     * can not be changed if the task is finished or has failed.
     * 
     * @throws Exception
     *             if argument.equals("") || ongoing() == false
     */
    public void setDescription(String description) throws Exception {
	checkOngoing();
	checkDescription(description);
	this.description = description;
    }

    /**
     * @return the user this task belongs to, not null
     */
    public String getUserName() {
	return user.getName();
    }

    /**
     * Sets the user this task belongs to the given User object. The User can not be null and can
     * not be changed if the task is finished or has failed.
     * 
     * @throws Exception
     *             if argument == null || ongoing() == false
     */
    public void setUser(User user) throws Exception {
	checkOngoing();
	checkUser(user);
	this.user = user;
    }

    /**
     * @return the estimated time (in minutes) needed to complete the task, not < 0
     */
    public int getEstimatedDuration() {
	return estimatedDuration;
    }

    /**
     * Sets the estimated duration (in minutes) of this task to the given Integer. The estimated
     * duration can not be negative and can not be changed if the task is finished or has failed.
     * 
     * @throws Exception
     *             if argument < 0 || ongoing() == false
     */
    public void setEstimatedDuration(int estimatedDuration) throws Exception {
	checkOngoing();
	checkDuration(estimatedDuration);
	this.estimatedDuration = estimatedDuration;
    }

    /**
     * @return a percentage that determines whether the task is finished on time, not < 0
     */
    public int getAcceptableDeviation() {
	return acceptableDeviation;
    }

    /**
     * Sets a percentage that determines whether the task is finished on time. This percentage can
     * not be negative and can not be changed if the task is finished or has failed.
     * 
     * @throws Exception
     *             if argument < 0 || ongoing() == false
     */
    public void setAcceptableDeviation(int acceptableDeviation) throws Exception {
	checkOngoing();
	checkDeviation(acceptableDeviation);
	this.acceptableDeviation = acceptableDeviation;
    }

    /**
     * @return a read-only list that contains the tasks that need to be completed before this task
     *         can be finished, can not contain null values and can not contain a loop
     */
    public List<Task> getDependencyTasks() {
	// returns a read-only list
	return Collections.unmodifiableList(dependencyTasks);
    }

    /**
     * Sets the dependency list of this task. The dependency list contains the tasks that need to be
     * completed before this task can be finished. This list may not contain null values, may not
     * cause loops and can not be changed if the task is finished or has failed.
     * 
     * @throws Exception
     *             if argument contains null values or causes a loop in the dependency graph or if
     *             ongoing() == false
     */
    public void setDependencyTasks(List<Task> dependencyTasks) throws Exception {
	checkOngoing();
	if (dependencyTasks == null) {
	    dependencyTasks = new ArrayList<Task>();
	} else {
	    checkDependencyTasks(dependencyTasks);
	}
	this.dependencyTasks = dependencyTasks;
    }

    /**
     * Adds a task to the dependency list. The added task or its alternate should be finished before
     * the task that owns the dependency list can be finished. The task can not be null, may not
     * cause a loop and can not be added if the task is finished or has failed.
     * 
     * @throws Exception
     *             if argument == null, argument causes a loop in the dependency graph or if
     *             ongoing() == false
     */
    public void addDependencyTask(Task dependencyTask) throws Exception {
	checkOngoing();
	checkDependencyTask(dependencyTask);
	this.dependencyTasks.add(dependencyTask);
    }

    /**
     * @return an alternate task if one is set, null otherwise
     */
    public Task getAlternateTask() {
	return alternateTask;
    }

    /**
     * Sets an alternate task. The alternate task can replace the task that owns it to finish a
     * project. Can not be changed if the task is finished or has failed.
     * 
     * @throws Exception
     *             if ongoing() == false or the given alternate task caused a loop
     */
    public void setAlternateTask(Task alternateTask) throws Exception {
	checkOngoing();
	checkAlternateTask(alternateTask);
	this.alternateTask = alternateTask;
    }

    /**
     * @return if the task is finished or has failed, a TimeSpan object that indicates the start and
     *         end time will be returned, null otherwise
     */
    public TimeSpan getTimeSpan() {
	return timeSpan;
    }

    /**
     * Sets the time span of an ongoing task.
     * 
     * @throws Exception
     *             if end time is before start time or ongoing() == false
     */
    public void setTimeSpan(TimeSpan timeSpan) throws Exception {
	checkOngoing();
	checkTimeSpan(timeSpan);
	this.timeSpan = timeSpan;
    }

    /**
     * @return the status of the task
     */
    public String getStatus() {
	if (ongoing()) {
	    if (available()) {
		return "available";
	    } else {
		return "unavailable";
	    }
	} else {
	    return status.getStatus();
	}
    }

    /**
     * @return true if the task is not finished and not failed, false otherwise
     */
    public boolean ongoing() {
	return status.ongoing();
    }

    /**
     * @return true if the task is finished, false otherwise
     */
    public boolean finished() {
	return status.finished();
    }

    /**
     * @return true if ongoing() == true and all dependencies are either finished or have a finished
     *         alternate, false otherwise
     */
    public boolean available() {
	if (!ongoing()) {
	    return false;
	} else {
	    for (Task task : getDependencyTasks()) {
		if (!task.finished()) {
		    if (!alternateTaskFinished(task)) {
			return false;
		    }
		}
	    }
	}
	return true;
    }

    /**
     * private method that recursively checks whether a task has a finished alternative
     */
    private boolean alternateTaskFinished(Task task) {
	Task alternateTask = task.getAlternateTask();
	if (alternateTask == null) {
	    return false;
	} else {
	    if (!alternateTask.finished()) {
		return alternateTaskFinished(alternateTask);
	    }
	}
	return true;
    }

    /**
     * Changes the status of an ongoing task. The argument can not be null, because a task must have
     * a status. The status can not be set to finished or failed, if the time span is not set. So
     * when setting the status to finished or failed, the time span must be set first. The status
     * can not be set to failed, if no alternative is set.
     * 
     * @throws Exception
     *             if argument == null || ongoing() == false || (argument == finished && timespan ==
     *             null) || (argument == failed && timespan == null) || (argument == failed &&
     *             alternate == null)
     */
    public void setStatus(Status status) throws Exception {
	checkOngoing();
	checkStatus(status);
	this.status = status;
    }

    /**
     * The string representation of a task object as shown in the assignment
     */
    @Override
    public String toString() {
	String result = "Task " + taskID + " " + getStatus() + ": " + description + ", "
		+ minutesToHoursAndMinutes() + ", " + acceptableDeviation + "% margin";
	for (Task task : dependencyTasks) {
	    result += ", depends on task " + task.getTaskID();
	}
	if (alternateTask != null) {
	    result += ", has alternate " + alternateTask.getTaskID();
	}
	if (!ongoing()) {
	    result += ", started " + timeSpan.getStartTime() + ", finished "
		    + timeSpan.getEndTime();
	}
	return result;
    }

    /**
     * @return the estimated hours and minutes to complete this task
     */
    private String minutesToHoursAndMinutes() {
	int minutes = estimatedDuration % 60;
	return (estimatedDuration / 60) + " hours " + (minutes == 0 ? "" : minutes + " minutes");
    }

    // Private methods for consistency checking

    private void checkOngoing() throws Exception {
	if (!ongoing()) {
	    throw new Exception("Finished or failed tasks can not be modified!");
	}
    }

    private void checkTaskID(int taskID) throws Exception {
	if (taskID < 0) {
	    throw new Exception("The task identifier must be larger or equal to 0!");
	}
    }

    private void checkDescription(String description) throws Exception {
	if (description == null || description.equals("")) {
	    throw new Exception("Task description can not be null or empty!");
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

    private void checkDeviation(int acceptableDeviation) throws Exception {
	if (acceptableDeviation < 0) {
	    throw new Exception("The acceptable deviation can not be negative!");
	}
    }

    private void checkDependencyTasks(List<Task> dependencyTasks) throws Exception {
	for (Task task : dependencyTasks) {
	    if (task == null) {
		throw new Exception("Can not add null to the list of dependency tasks!");
	    }
	    if (this == task) {
		throw new Exception("A task can not depend on itself!");
	    }
	}
	List<Task> notAllowed = new ArrayList<Task>();
	notAllowed.add(this);
	dependencyLoopCheck(dependencyTasks, notAllowed);
    }

    private void checkDependencyTask(Task dependencyTask) throws Exception {
	if (dependencyTask == null) {
	    throw new Exception("Can not add null to the list of dependency tasks!");
	}
	if (this == dependencyTask) {
	    throw new Exception("A task can not depend on itself!");
	}
	List<Task> tasks = new ArrayList<Task>();
	tasks.add(dependencyTask);
	List<Task> notAllowed = new ArrayList<Task>();
	notAllowed.add(this);
	dependencyLoopCheck(tasks, notAllowed);
    }

    private void dependencyLoopCheck(List<Task> tasks, List<Task> notAllowed) throws Exception {
	for (Task task : tasks) {
	    if (notAllowed.contains(task)) {
		throw new Exception("The given dependencies caused a loop in the dependency graph!");
	    }
	    List<Task> notAllowedNew = new ArrayList<Task>(notAllowed);
	    notAllowedNew.add(task);
	    dependencyLoopCheck(task.getDependencyTasks(), notAllowedNew);
	}
    }

    private void checkAlternateTask(Task alternateTask) throws Exception {
	if (finished()) {
	    throw new Exception("Can not modify the alternate task of a finished task!");
	}
	// If the alternate task is not null, check for loop
	if (alternateTask != null) {
	    // An alternate task can not be itself
	    if (this == alternateTask) {
		throw new Exception("A task can not be an alternate for itself!");
	    }
	    // Loop check
	    List<Task> notAllowed = new ArrayList<Task>();
	    notAllowed.add(this);
	    alternateTaskLoopCheck(alternateTask, notAllowed);
	}
    }

    private void alternateTaskLoopCheck(Task task, List<Task> notAllowed) throws Exception {
	if (task != null) {
	    if (notAllowed.contains(task)) {
		throw new Exception("The given alternate caused a loop!");
	    }
	    notAllowed.add(task);
	    alternateTaskLoopCheck(task.getAlternateTask(), notAllowed);
	}
    }

    private void checkTimeSpan(TimeSpan timeSpan) throws Exception {
	// If the time span is set, the end time may not be before the start time
	if (timeSpan != null && timeSpan.getStartTime() > timeSpan.getEndTime()) {
	    throw new Exception("The start time has to be before or equal to the end time!");
	}
    }

    private void checkStatus(Status status) throws Exception {
	// The status can not be null
	if (status == null) {
	    throw new Exception("A task must have a status!");
	}
	// The status can only be set to finished if the time span is set
	if (status.finished() && this.timeSpan == null) {
	    throw new Exception("A task can only be finished if it has a time span!");
	}
	// The status can only be set to failed if the time span is set
	if ((!status.finished() && !status.ongoing()) && this.timeSpan == null) {
	    throw new Exception(
		    "The task status can only be set to failed if the time span is set!");
	}
	// The status can only be set to failed if an alternate is set
	if ((!status.finished() && !status.ongoing()) && this.alternateTask == null) {
	    throw new Exception("The task status can only be set to failed if an alternate is set!");
	}
    }
}