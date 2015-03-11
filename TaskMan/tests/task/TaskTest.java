package task;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TaskTest {

    /**
     * This test checks whether the task constructor accepts invalid arguments
     */
    @Test
    public void testTaskConstructor() {
	// When creating invalid tasks, an exception must be thrown, otherwise the test fails

	// Check invalid ID
	try {
	    Task task = new Task(-1, "test", new User("user"), 60, 5, new ArrayList<Task>(), null);
	    fail("Task with negative ID created!");
	} catch (Exception e) {
	}

	// Check empty description
	try {
	    Task task = new Task(0, "", new User("user"), 60, 5, new ArrayList<Task>(), null);
	    fail("Task with empty description created!");
	} catch (Exception e) {
	}

	// Check null description
	try {
	    Task task = new Task(0, null, new User("user"), 60, 5, new ArrayList<Task>(), null);
	    fail("Task with without description created!");
	} catch (Exception e) {
	}

	// Check null user
	try {
	    Task task = new Task(0, "test", null, 60, 5, new ArrayList<Task>(), null);
	    fail("Task with without user created!");
	} catch (Exception e) {
	}

	// Check negative duration
	try {
	    Task task = new Task(0, "test", new User("user"), -60, 5, new ArrayList<Task>(), null);
	    fail("Task with negative duration created!");
	} catch (Exception e) {
	}

	// Check negative deviation
	try {
	    Task task = new Task(0, "test", new User("user"), 60, -5, new ArrayList<Task>(), null);
	    fail("Task with negative duration created!");
	} catch (Exception e) {
	}

	// Check null values in dependency list
	List<Task> dependencies = new ArrayList<Task>();
	dependencies.add(null);
	try {
	    Task task = new Task(0, "test", new User("user"), 60, 5, dependencies, null);
	    fail("Task with null list created!");
	} catch (Exception e) {
	}

	// The constructor doesn't have to check for loops in dependency graph because it is
	// creating a new object that no one else can reference at that moment. So the creation of
	// the new task can not cause a loop. To test for loops in the dependency graph, we will
	// test the setDependencyTasks(list) method later.
    }

    /**
     * This test checks whether the setters accepts invalid arguments
     */
    @Test
    public void testTaskSetters() {

    }
}
