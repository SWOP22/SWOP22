package task;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import user.User;
import data.TaskData;
import data.TaskUpdateData;

public class TaskTest {

    /**
     * This test checks whether the task constructor accepts invalid arguments or rejects valid
     * arguments
     */
    @Test
    public void testConstructor() {
	Task task0 = null;
	List<Task> dependencies = new ArrayList<Task>();

	// Create a task with valid arguments
	try {
	    task0 = new Task(0, "test", new User("user"), 60, 5, dependencies, null);
	} catch (Exception e) {
	    fail("Failed to create task with valid arguments!");
	}

	// Check if dependencies are stored correctly, so that we can not edit the list directly
	dependencies.add(null);
	assertNotEquals(dependencies, task0.getDependencyTasks());

	// Check if status is correctly set
	assertEquals("ongoing", task0.getStatus());

	// Check if time span is correctly set
	assertNotEquals(null, task0.getTimeSpan());
	assertEquals(null, task0.getTimeSpan().getStartTime());
	assertEquals(null, task0.getTimeSpan().getEndTime());

	try {
	    task0 = new Task(0, "test", new User("user"), 60, 5, null, null);
	} catch (Exception e) {
	    fail("Failed to create task with valid arguments!");
	}

	// Create a task using a TaskData argument with valid arguments
	TaskData taskData = new TaskData(null);
	taskData.setDescription("test");
	try {
	    taskData.setUser(new User("user"));
	} catch (Exception e) {
	    fail("Failed to create a user!");
	}
	taskData.setEstimatedDuration(60);
	taskData.setAcceptableDeviation(5);
	taskData.setDependencyTasks(null);
	taskData.setAlternateFor(null);

	try {
	    task0 = new Task(0, taskData);
	} catch (Exception e) {
	    fail("Failed to create task with valid arguments!");
	}

	// When creating invalid tasks, an exception must be thrown, otherwise the test fails
	// Because one of the two constructors uses the other, most things only have to be tested
	// once

	// Check invalid ID
	try {
	    task0 = new Task(-1, "test", new User("user"), 60, 5, new ArrayList<Task>(), null);
	    fail("Task with negative ID created!");
	} catch (Exception e) {
	}

	// Check empty description
	try {
	    task0 = new Task(0, "", new User("user"), 60, 5, new ArrayList<Task>(), null);
	    fail("Task with empty description created!");
	} catch (Exception e) {
	}

	// Check null description
	try {
	    task0 = new Task(0, null, new User("user"), 60, 5, new ArrayList<Task>(), null);
	    fail("Task without description created!");
	} catch (Exception e) {
	}

	// Check null user
	try {
	    task0 = new Task(0, "test", null, 60, 5, new ArrayList<Task>(), null);
	    fail("Task without user created!");
	} catch (Exception e) {
	}

	// Check negative duration
	try {
	    task0 = new Task(0, "test", new User("user"), -60, 5, new ArrayList<Task>(), null);
	    fail("Task with negative duration created!");
	} catch (Exception e) {
	}

	// Check negative deviation
	try {
	    task0 = new Task(0, "test", new User("user"), 60, -5, new ArrayList<Task>(), null);
	    fail("Task with negative duration created!");
	} catch (Exception e) {
	}

	// Check null values in dependency list
	dependencies = new ArrayList<Task>();
	dependencies.add(null);
	try {
	    task0 = new Task(0, "test", new User("user"), 60, 5, dependencies, null);
	    fail("Task with null list created!");
	} catch (Exception e) {
	}

	// Check if 2nd constructor accepts a null TaskData value
	try {
	    task0 = new Task(0, null);
	    fail("Task with null argument created!");
	} catch (NullPointerException nullPointerException) {
	} catch (Exception ex) {
	    fail("NullPointerException expected!");
	}

	// The constructor doesn't have to check for loops in dependency graph because it is
	// creating a new object that no one else can reference at that moment. So the creation of
	// the new task can not cause a loop. To test for loops in the dependency graph, we will
	// test the setDependencyTasks(list) and the addDependencyTask(task) method later.
    }

    /**
     * This test checks whether the getters return the right values and whether the update method
     * and the setters accept invalid arguments or reject valid arguments. Because a lot of checks
     * are already tested in the constructor (uses the same private check methods), we will not test
     * these again.
     */
    @Test
    public void testGettersSettersAndUpdateMethod() {
	Task task0 = null;

	// Create a task with valid arguments
	try {
	    task0 = new Task(0, "test", new User("user"), 60, 5, new ArrayList<Task>(), null);
	} catch (Exception e) {
	    fail("Failed to create task with valid arguments!");
	}

	// Check getter for the task identifier
	assertEquals(0, task0.getTaskID());

	// Checks getter and setter for the description of a task
	try {
	    task0.setDescription("test2");
	} catch (Exception e) {
	    fail("Could not change the description of a task!");
	}

	assertEquals("test2", task0.getDescription());

	// Checks getter and setter for the user of a task
	try {
	    task0.setUser(new User("user2"));
	} catch (Exception e) {
	    fail("Could not change the user of a task!");
	}

	assertEquals("user2", task0.getUserName());

	// Checks getter and setter for the estimated duration of a task
	try {
	    task0.setEstimatedDuration(120);
	} catch (Exception e) {
	    fail("Could not change the estimated duration of a task!");
	}

	assertEquals(120, task0.getEstimatedDuration());

	// Checks getter and setter for the acceptable deviation of a task
	try {
	    task0.setAcceptableDeviation(10);
	} catch (Exception e) {
	    fail("Could not change the acceptable deviation of a task!");
	}

	assertEquals(10, task0.getAcceptableDeviation());

	// Create a task with valid arguments
	Task task1 = null;

	try {
	    task1 = new Task(1, "test", new User("user"), 60, 5, new ArrayList<Task>(), null);
	} catch (Exception e) {
	    fail("Failed to create task with valid arguments!");
	}

	// Checks getter and setter for the dependecy task
	try {
	    task0.setDependencyTasks(null);
	} catch (Exception e) {
	    fail("Could not change the dependecy task list!");
	}

	List<Task> dependencyTasks = new ArrayList<Task>();
	dependencyTasks.add(task1);

	try {
	    task0.setDependencyTasks(dependencyTasks);
	} catch (Exception e) {
	    fail("Could not change the dependecy task list!");
	}

	assertEquals(dependencyTasks, task0.getDependencyTasks());

	// Check list unmodifiable
	List<Task> unmodifiableDependencyTasks = task0.getDependencyTasks();

	try {
	    unmodifiableDependencyTasks.clear();
	    fail("Could clear an unmodifiable list!");
	} catch (Exception e) {

	}

	assertEquals(dependencyTasks, unmodifiableDependencyTasks);

	// Check addDependencyTask method
	try {
	    task0.addDependencyTask(null);
	    fail("Could add null to the dependency tasks!");
	} catch (Exception e) {
	}

	try {
	    task0.addDependencyTask(task0);
	    fail("Could add itself to the dependency tasks!");
	} catch (Exception e) {
	}

	try {
	    task0.setDependencyTasks(null);
	} catch (Exception e) {
	    fail("Could not supply the null argument to setDependencyTasks!");
	}

	try {
	    task0.addDependencyTask(task1);
	} catch (Exception e) {
	    fail("Could not add a valid dependency task!");
	}

	// Check addDependencyTask allows to add doubles
	try {
	    task0.addDependencyTask(task1);
	} catch (Exception e) {
	}

	assertEquals(1, task0.getDependencyTasks().size());

	// Check addDependencyTask allows loop
	try {
	    task1.addDependencyTask(task0);
	    fail("Could create a loop in the dependency graph!");
	} catch (Exception e) {
	}

	// Check setDependencyTasks allows to add doubles
	dependencyTasks = new ArrayList<Task>();
	dependencyTasks.add(task1);
	dependencyTasks.add(task1);

	try {
	    task0.setDependencyTasks(dependencyTasks);
	} catch (Exception e) {
	}

	assertEquals(1, task0.getDependencyTasks().size());

	// Check loop in dependency tasks
	Task task2 = null;

	try {
	    task2 = new Task(2, "test", new User("user"), 80, 5, null, null);
	} catch (Exception e) {
	    fail("Failed to create task with valid arguments!");
	}

	dependencyTasks = new ArrayList<Task>();
	dependencyTasks.add(task2);

	try {
	    task1.setDependencyTasks(dependencyTasks);
	} catch (Exception e) {
	    fail("Could not set a valid dependency task list!");
	}

	// At this moment task0 depends on task1 and task1 depends on task2
	// Check if let task2 depend on task0 and cause a loop
	dependencyTasks = new ArrayList<Task>();
	dependencyTasks.add(task0);

	try {
	    task2.setDependencyTasks(dependencyTasks);
	    fail("Could create a loop in the dependency graph!");
	} catch (Exception e) {
	}

	// Try to add itself to dependency graph with setDependencyTask method
	dependencyTasks = new ArrayList<Task>();
	dependencyTasks.add(task0);

	try {
	    task0.setDependencyTasks(dependencyTasks);
	    fail("Could add itself to the dependecy task list!");
	} catch (Exception e) {
	}

	// Check updateStatus
	try {
	    task2.updateTask(null);
	    fail("updateTask allowed a null argument!");
	} catch (Exception e) {
	}

	// Check if it accepts TaskUpdateData for another task
	// project may be null to test task class
	TaskUpdateData taskUpdateData = new TaskUpdateData(task0, null);

	try {
	    task2.updateTask(taskUpdateData);
	    fail("updateTask accepted TaskUpdateData for another task!");
	} catch (Exception e) {
	}

	// Now with the correct task
	taskUpdateData = new TaskUpdateData(task2, null);

	try {
	    task2.updateTask(taskUpdateData);
	} catch (Exception e) {
	    fail("Rejected a valid TaskUpdateData object!");
	}

	// Update start time
	taskUpdateData.setStartTime(LocalDateTime.of(2015, 1, 1, 12, 0));

	try {
	    task2.updateTask(taskUpdateData);
	} catch (Exception e) {
	    fail("Rejected a valid TaskUpdateData object!");
	}

	assertEquals(LocalDateTime.of(2015, 1, 1, 12, 0), task2.getTimeSpan().getStartTime());

	// Update end time without updating status to finished/failed
	taskUpdateData = new TaskUpdateData(task2, null);
	taskUpdateData.setEndTime(LocalDateTime.of(2015, 1, 1, 13, 0));

	try {
	    task2.updateTask(taskUpdateData);
	    fail("Accepted a invalid TaskUpdateData object!");
	} catch (Exception e) {
	}

	// Update status to finished without setting end time
	taskUpdateData = new TaskUpdateData(task2, null);
	taskUpdateData.setStatus(new Finished());

	try {
	    task2.updateTask(taskUpdateData);
	    fail("Accepted a invalid TaskUpdateData object!");
	} catch (Exception e) {
	}

	// Update status to failed without setting end time
	taskUpdateData = new TaskUpdateData(task2, null);
	taskUpdateData.setStatus(new Failed());

	try {
	    task2.updateTask(taskUpdateData);
	    fail("Accepted a invalid TaskUpdateData object!");
	} catch (Exception e) {
	}

	// Update end time and status to finished, for a task that has no start time
	try {
	    task2 = new Task(2, "test", new User("user"), 80, 5, null, null);
	} catch (Exception e) {
	    fail("Failed to create task with valid arguments!");
	}

	taskUpdateData = new TaskUpdateData(task2, null);
	taskUpdateData.setEndTime(LocalDateTime.of(2015, 1, 1, 13, 0));
	taskUpdateData.setStatus(new Finished());

	try {
	    task2.updateTask(taskUpdateData);
	    fail("Accepted a invalid TaskUpdateData object!");
	} catch (Exception e) {
	}

	// Update end time and status to finished, for a task that with a start time
	taskUpdateData = new TaskUpdateData(task2, null);
	taskUpdateData.setStartTime(LocalDateTime.of(2015, 1, 1, 12, 0));
	taskUpdateData.setEndTime(LocalDateTime.of(2015, 1, 1, 13, 0));
	taskUpdateData.setStatus(new Finished());

	try {
	    task2.updateTask(taskUpdateData);
	} catch (Exception e) {
	    fail("Rejected a valid TaskUpdateData object!");
	}

	assertEquals(true, task2.finished());

	// Edit a finished task
	try {
	    task2.updateTask(taskUpdateData);
	    fail("Could edit a finished task!");
	} catch (Exception e) {
	}

	// Set end time with ongoing status
	try {
	    task2 = new Task(2, "test", new User("user"), 80, 5, null, null);
	} catch (Exception e) {
	    fail("Failed to create task with valid arguments!");
	}

	taskUpdateData = new TaskUpdateData(task2, null);
	taskUpdateData.setStartTime(LocalDateTime.of(2015, 1, 1, 12, 0));
	taskUpdateData.setEndTime(LocalDateTime.of(2015, 1, 1, 13, 0));
	taskUpdateData.setStatus(new Ongoing());

	try {
	    task2.updateTask(taskUpdateData);
	    fail("Accepted a invalid TaskUpdateData object!");
	} catch (Exception e) {
	}

	// Update end time and status to failed, for a task that with a start time
	taskUpdateData = new TaskUpdateData(task2, null);
	taskUpdateData.setStartTime(LocalDateTime.of(2015, 1, 1, 12, 0));
	taskUpdateData.setEndTime(LocalDateTime.of(2015, 1, 1, 13, 0));
	taskUpdateData.setStatus(new Failed());

	try {
	    task2.updateTask(taskUpdateData);
	} catch (Exception e) {
	    fail("Rejected a valid TaskUpdateData object!");
	}

	assertEquals(true, task2.failed());

	// Check alternateFor getter and setter
	// Set alternate for a task that has not failed
	try {
	    task0.setAlternateFor(task1);
	    fail("Could set a task as alternate for a task that hasn't failed!");
	} catch (Exception e) {
	}

	// Set alternate for a task that has failed
	try {
	    task0.setAlternateFor(task2);
	} catch (Exception e) {
	    fail("Could not set an alternative for a failed task!");
	}

	assertEquals(task2, task0.getAlternateFor());

	// Check overlapping time spans
	Task task3 = null, task4 = null;

	try {
	    task3 = new Task(3, "test", new User("user"), 80, 5, null, null);
	} catch (Exception e) {
	    fail("Failed to create task with valid arguments!");
	}
	try {
	    task4 = new Task(4, "test", new User("user"), 80, 5, null, null);
	} catch (Exception e) {
	    fail("Failed to create task with valid arguments!");
	}
	try {
	    task4.addDependencyTask(task3);
	} catch (Exception e1) {
	    fail("Failed to add valid dependency!");
	}

	// Try starting task4 when its dependency task3 hasn't ended
	taskUpdateData = new TaskUpdateData(task4, null);
	taskUpdateData.setStartTime(LocalDateTime.of(2015, 1, 1, 12, 0));

	try {
	    task4.updateTask(taskUpdateData);
	    fail("Accepted overlapping time span!");
	} catch (Exception e) {
	}

	taskUpdateData = new TaskUpdateData(task3, null);
	taskUpdateData.setStartTime(LocalDateTime.of(2015, 1, 1, 12, 0));
	taskUpdateData.setEndTime(LocalDateTime.of(2015, 1, 1, 13, 0));
	taskUpdateData.setStatus(new Finished());

	try {
	    task3.updateTask(taskUpdateData);
	} catch (Exception e) {
	    fail("Rejected valid update!");
	}

	// Try starting task4 with a start time before the end time of its dependency
	taskUpdateData = new TaskUpdateData(task4, null);
	taskUpdateData.setStartTime(LocalDateTime.of(2015, 1, 1, 12, 30));

	try {
	    task4.updateTask(taskUpdateData);
	    fail("Accepted overlapping time span!");
	} catch (Exception e) {
	}

	// Try starting task4 with a valid start time
	taskUpdateData = new TaskUpdateData(task4, null);
	taskUpdateData.setStartTime(LocalDateTime.of(2015, 1, 1, 13, 00));

	try {
	    task4.updateTask(taskUpdateData);
	} catch (Exception e) {
	    fail("Rejected valid start time!");
	}

	// Try to modify the dependency list of a started task
	try {
	    task4.setDependencyTasks(null);
	    fail("Could edit the dependencies of a started task!");
	} catch (Exception e) {
	}
	try {
	    task4.addDependencyTask(task3);
	    fail("Could edit the dependencies of a started task!");
	} catch (Exception e) {
	}

	// Check to string method
	assertEquals(
		"Task 0 ongoing: test2, 2 hours, 10% margin, depends on task 1, alternate for 2",
		task0.toString());

	assertEquals("Task 3 finished: test, 1 hour 20 minutes, 5% margin, started "
		+ task3.getTimeSpan().getStartTime() + ", finished "
		+ task3.getTimeSpan().getEndTime(), task3.toString());

	try {
	    task4.setEstimatedDuration(1);
	} catch (Exception e) {
	    fail("Could not set a valid estimated duration!");
	}

	assertEquals("Task 4 ongoing: test, 1 minute, 5% margin, depends on task 3",
		task4.toString());

	try {
	    task4.setEstimatedDuration(121);
	} catch (Exception e) {
	    fail("Could not set a valid estimated duration!");
	}

	assertEquals("Task 4 ongoing: test, 2 hours 1 minute, 5% margin, depends on task 3",
		task4.toString());

	try {
	    task4.setEstimatedDuration(0);
	} catch (Exception e) {
	    fail("Could not set a valid estimated duration!");
	}

	assertEquals("Task 4 ongoing: test, 0 minutes, 5% margin, depends on task 3",
		task4.toString());
    }
}
