package task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import time.WorkTime;
import user.User;
import data.TaskData;
import data.TaskUpdateData;

/**
 * Task class to represent the task objects as described in the assignment.
 * 
 * To guarantee consistency, finished and failed tasks can not be modified! Therefore, you should
 * update all the relevant information before changing the status to finished or failed. To prevent
 * undesirable scenarios, we added exceptions to alert the user if certain conditions aren't met.
 * 
 * Because we wanted the minimize the coupling between the Project and the Task classes, the
 * association between them is unidirectional. Tasks have no information about the projects that own
 * them and the other tasks of the project that owns them. This way, tasks can not guarantee that
 * their dependencies are tasks of the same project and, because they can not check for alternate
 * tasks if one of their dependencies has failed, they can not determine their availability
 * themselves.
 */
public class Task {
    private int taskID;
    private String description;
    private User user;
    private int estimatedDuration; // in minutes
    private int acceptableDeviation; // represents a percentage
    private List<Task> dependencyTasks;
    private Task alternateFor;
    private TimeSpan timeSpan;
    private Status status;

    /**
     * Constructor for the Task class. To simplify the consistency checking, the status of a new
     * task is always "ongoing" and the time span is always initialized with start and end time ==
     * null.
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
     * @param alternateFor
     *            this task can replace the alternateFor to complete the project, must be a failed
     *            task or null
     * @throws Exception
     *             if the given arguments are not valid, an exception will be thrown
     */
    public Task(int taskID, String description, User user, int estimatedDuration,
	    int acceptableDeviation, List<Task> dependencyTasks, Task alternateFor)
	    throws Exception {
	// New tasks don't have a start/end time and have the status ongoing
	this.timeSpan = new TimeSpan(null, null);
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
	checkAlternateFor(alternateFor);

	// After all arguments are checked, assign values
	this.taskID = taskID;
	this.description = description;
	this.user = user;
	this.estimatedDuration = estimatedDuration;
	this.acceptableDeviation = acceptableDeviation;
	// New list for data encapsulation
	this.dependencyTasks = new ArrayList<Task>(dependencyTasks);
	this.alternateFor = alternateFor;
    }

    /**
     * Constructor for the task class that uses a TaskData object to initialize its data. Uses the
     * other constructor to avoid double code. The given TaskData object can not be null!
     * 
     * @param taskID
     *            identifier for this task, ID's start from 0
     * @param taskData
     *            except for the task ID, the TaskData object must supply all the arguments of the
     *            other constructor via get methods
     * @throws Exception
     *             if the given arguments are not valid, an exception will be thrown
     * @throws NullPointerException
     *             if taskData == null
     */
    public Task(int taskID, TaskData taskData) throws Exception, NullPointerException {
	this(taskID, taskData.getDescription(), taskData.getUser(),
		taskData.getEstimatedDuration(), taskData.getAcceptableDeviation(), taskData
			.getDependencyTasks(), taskData.getAlternateFor());
    }

    /**
     * Method for updating the start time, the end time or the status. If the status gets set to
     * finished or failed, the end time must be set as well and if the end time gets set, the status
     * must be set to finished or failed. The TaskUpdateData object can not be null and must relate
     * to the same task. Other null values in the TaskUpdateData object are ignored.
     * 
     * @throws Exception
     *             if invalid update data or task.ongoing() == false
     */
    public void updateTask(TaskUpdateData updateData) throws Exception {
	checkOngoing();

	if (updateData == null) {
	    throw new Exception("Can not update a task with a null object!");
	}
	if (this != updateData.getTask()) {
	    throw new Exception("A task can not update another task!");
	}

	if (updateData.getStartTime() != null) {
	    // the set method will perform all the necessary checks
	    setStartTime(updateData.getStartTime());
	}

	// if the update data has an end time, it must have a finished/failed status
	if (updateData.getEndTime() != null) {
	    if (updateData.getStatus() == null) {
		throw new Exception(
			"Can not set the end time without setting the status to finished or failed!");
	    }
	    if (updateData.getStatus().ongoing()) {
		throw new Exception(
			"Can not set the end time without setting the status to finished or failed!");
	    }
	    // the setter will perform all the other checks
	    setEndTime(updateData.getEndTime());
	}

	// the set method guarantees that the status can not be changed to finished or failed,
	// if the start or end time is not set
	if (updateData.getStatus() != null) {
	    setStatus(updateData.getStatus());
	}
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
     * @return the estimated time this task should be finished, null if the task hasn't started, the
     *         exact end time if the task is finished or has failed
     */
    public LocalDateTime getEstimatedEndTime() {
	if (timeSpan.getStartTime() == null) {
	    return null;
	}
	if (!ongoing()) {
	    return timeSpan.getEndTime();
	} else {
	    return WorkTime.getEstimatedEndTime(timeSpan.getStartTime(), estimatedDuration);
	}
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
	if (timeSpan.getStartTime() != null) {
	    throw new Exception(
		    "Can not modify the dependency list of a task that has already started!");
	}
	if (dependencyTasks == null) {
	    dependencyTasks = new ArrayList<Task>();
	} else {
	    checkDependencyTasks(dependencyTasks);
	}
	// Create Set to delete doubles, use Set to create new List
	Set<Task> uniqueDependencyTasks = new HashSet<Task>(dependencyTasks);
	// The new List also ensures data encapsulation, the list can only be modified via Setters
	// and not directly
	this.dependencyTasks = new ArrayList<Task>(uniqueDependencyTasks);
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
	if (timeSpan.getStartTime() != null) {
	    throw new Exception(
		    "Can not modify the dependency list of a task that has already started!");
	}
	checkDependencyTask(dependencyTask);
	if (!dependencyTasks.contains(dependencyTask)) {
	    dependencyTasks.add(dependencyTask);
	}
    }

    /**
     * @return a task that this task can replace if one is set, null otherwise
     */
    public Task getAlternateFor() {
	return alternateFor;
    }

    /**
     * Sets this task as an alternate. An alternate task can replace a failed task to complete the
     * project.
     * 
     * @throws Exception
     *             if ongoing() == false or alternateFor.failed() == false
     */
    public void setAlternateFor(Task alternateFor) throws Exception {
	checkOngoing();
	checkAlternateFor(alternateFor);
	this.alternateFor = alternateFor;
    }

    /**
     * @return a TimeSpan object that contains the start and end time of this task, if they are set
     */
    public TimeSpan getTimeSpan() {
	return timeSpan;
    }

    /**
     * Sets the start time of an ongoing task.
     * 
     * @throws Exception
     *             if the given startTime == null, if the end time is also set and before the given
     *             start time, if the given start time is before the end time of any of the tasks'
     *             dependencies or if ongoing() == false
     */
    private void setStartTime(LocalDateTime startTime) throws Exception {
	checkOngoing();
	checkSetStartTime(startTime);
	timeSpan.setStartTime(startTime);
    }

    /**
     * Sets the end time of an ongoing task.
     * 
     * @throws Exception
     *             if the given endTime == null, if the start time is not set, if the given end time
     *             is before the start time or if ongoing() == false
     */
    private void setEndTime(LocalDateTime endTime) throws Exception {
	checkOngoing();
	checkSetEndTime(endTime);
	timeSpan.setEndTime(endTime);
    }

    /**
     * @return the status of the task, availability must be checked by the project that owns this
     *         task
     */
    public String getStatus() {
	return status.getStatus();
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
     * @return true if the task has failed, false otherwise
     */
    public boolean failed() {
	return status.failed();
    }

    /**
     * Changes the status of an ongoing task. The argument can not be null, because a task must have
     * a status. The status can not be set to finished or failed, if the start and end time are not
     * set. If a task is set to finished or failed, it can no longer be modified!
     * 
     * @throws Exception
     *             if argument == null || ongoing() == false || (argument == finished &&
     *             (timeSpan.getStartTime() == null || timeSpan.getEndTime() == null)) || (argument
     *             == failed && (timeSpan.getStartTime() == null || timeSpan.getEndTime() == null))
     */
    private void setStatus(Status status) throws Exception {
	checkOngoing();
	checkStatus(status);
	this.status = status;
    }

    /**
     * The string representation of a task object as shown in the assignment (except for
     * availability)
     */
    @Override
    public String toString() {
	String estimatedHourMin = minutesToHoursAndMinutes();

	String result = "Task " + taskID + " " + getStatus() + ": " + description + ", ";
	result += (estimatedHourMin.equals("") ? "0 minutes, " : estimatedHourMin + ", ");
	result += acceptableDeviation + "% margin";

	for (Task task : dependencyTasks) {
	    result += ", depends on task " + task.getTaskID();
	}
	if (alternateFor != null) {
	    result += ", alternate for " + alternateFor.getTaskID();
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
	int hours = (estimatedDuration / 60);
	int minutes = estimatedDuration % 60;
	String hourStr = "", minStr = "", result = "";

	if (hours == 1) {
	    hourStr = "1 hour";
	} else if (hours > 1) {
	    hourStr = hours + " hours";
	}

	if (minutes == 1) {
	    minStr = "1 minute";
	} else if (minutes > 1) {
	    minStr = minutes + " minutes";
	}

	if (hourStr.equals("")) {
	    result = minStr;
	} else {
	    if (minStr.equals("")) {
		result = hourStr;
	    } else {
		result = hourStr + " " + minStr;
	    }
	}

	return result;
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

    private void checkAlternateFor(Task alternateFor) throws Exception {
	// If the alternate task is not null, check if failed
	if (alternateFor != null) {
	    if (!alternateFor.failed()) {
		throw new Exception("A task can only be an alternate for a failed task!");
	    }
	}
    }

    private void checkSetStartTime(LocalDateTime startTime) throws Exception {
	if (startTime == null) {
	    throw new Exception("Can not set the start time to null!");
	}
	// check if the given start time is after the end time of all the tasks this task depends on
	for (Task task : dependencyTasks) {
	    if (task.getTimeSpan().getEndTime() == null) {
		throw new Exception(
			"Start time not accepted because a dependency has not yet finished!");
	    }
	    if (startTime.isBefore(task.getTimeSpan().getEndTime())) {
		throw new Exception(
			"Start time not accepted because a dependency has not yet finished!");
	    }
	}
	// The TimeSpan class guarantees that, if set, the end time can not be before a start time
    }

    private void checkSetEndTime(LocalDateTime endTime) throws Exception {
	if (endTime == null) {
	    throw new Exception("Can not set the end time to null!");
	}
	if (timeSpan.getStartTime() == null) {
	    throw new Exception("Can not set the end time if the start time is not set!");
	}
	// The TimeSpan class guarantees that, if set, the end time can not be before a start time
    }

    private void checkStatus(Status status) throws Exception {
	// The status can not be null
	if (status == null) {
	    throw new Exception("A task must have a status!");
	}
	// The status can only be set to finished if the time span is set
	if (status.finished() && (timeSpan.getStartTime() == null || timeSpan.getEndTime() == null)) {
	    throw new Exception("A task can only be finished if it has a start and end time!");
	}
	// The status can only be set to failed if the time span is set
	if (status.failed() && (timeSpan.getStartTime() == null || timeSpan.getEndTime() == null)) {
	    throw new Exception(
		    "The task status can only be set to failed if the start and end time are set!");
	}
    }
}