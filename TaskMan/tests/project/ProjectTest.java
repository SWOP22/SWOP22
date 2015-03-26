package project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import task.Task;
import user.User;
import data.ProjectData;

public class ProjectTest {

    /**
     * This test checks whether the project constructor accepts invalid arguments
     */
	@Test
    public void testProjectConstructor() {
	
    	Project project0 = null;
    	ProjectData pData0 = new ProjectData();
    	List<Task> allTasks = new ArrayList<Task>();
    	
    	// Create a project with valid arguments
    	try {
    		pData0.setName("test name");
			pData0.setDescription("test description");
			pData0.setCreationTime(LocalDateTime.now());
			pData0.setDueTime(LocalDateTime.of(2016,03,12,16,59));
    		
    		project0 = new Project(0, pData0);
    	} catch (Exception e) {
    		fail("Failed to create project with valid arguments!");
    	}
    	
    	//Check if the system returns a toString for a Project
    	try {
    		project0.toString();
    	}catch (Exception e) {
    		fail("Cannot return a project.toString()!");
    	}
    	
    	// Check if the tasks are stored correctly, so that cannot edit the list directly
    	allTasks.add(null);
    	assertNotEquals(allTasks, project0.getAllTasks());
    	
    	// Check if status is correctly set
    	assertEquals("ongoing", project0.getStatus());
    	
    	
    	Task task0 = null;
    	List<Task> dependencies = new ArrayList<Task>();
    			
    	// Create a task with valid arguments
    	try {
    	    task0 = new Task(0, "test", new User("user"), 60, 5, dependencies, null);
    	} catch (Exception e) {
    	    fail("Failed to create task with valid arguments!");
    	}
    	
    	// Check if all tasks are finished
    	assertEquals(false, project0.checkFinished());
    	
    	//Set task to finished
    	try {
    	    //task0.updateTask(updateData);
    	} catch (Exception e) {
    	    fail("Failed to set task status to finished!");
    	}
    	
    	// Check if the alternative of a status is finished
    	assertEquals(false, project0.checkAlternativeStatus(task0));
    	
    	// Check if a task is available
    	assertEquals(false, project0.checkTaskAvailability(task0));
    	
    	// Check invalid ID
		try {
			project0 = new Project(-1, pData0);
			
			fail("Project with negative ID created!");
		} catch (Exception e) {
		}
	
		// Check empty description
		try {
			pData0.setDescription("");
		    
			project0 = new Project(0, pData0);
			
			fail("Project with empty description created!");
		} catch (Exception e) {
		}
	
		// Check null description
		try {
			pData0.setDescription(null);
			
			project0 = new Project(0, pData0);
			
			fail("Project without description created!");
		} catch (Exception e) {
			
		}
	
		// Check null creation time
		try {
			pData0.setCreationTime(null);
		    
			project0 = new Project(0, pData0);
			
			fail("Project without creation time created!");
		} catch (Exception e) {
		}
		
		// Check null due time
			try {
				pData0.setDueTime(null);
				
				project0 = new Project(0, pData0);
				
				fail("Project without due time created!");
			} catch (Exception e) {
			}
	
		// Check creation time after due time
		try {
			pData0.setCreationTime(LocalDateTime.now());
			pData0.setDueTime(LocalDateTime.of(2000,03,12,16,59));
		    
			project0 = new Project(0, pData0);
			
			fail("Project created with a due time before the creation time!");
		} catch (Exception e) {
		}
	}
	
	/**
	 * This test checks whether getters return the right values and setters accept or reject invalid arguments.
	 * 
	 */
	@Test
	public void testGettersSetters() {
		Project project0 = null;
		ProjectData pData0 = new ProjectData();
		
		// Create a project with valid arguments
		try {
			pData0.setName("test name");
			pData0.setDescription("test description");
			pData0.setCreationTime(LocalDateTime.now());
			pData0.setDueTime(LocalDateTime.of(2016,03,12,16,59));
		    
			project0 = new Project(0, pData0);
		}
		catch (Exception e) {
			fail("Failed to created project with valid arguments!");
		}
		
		// Check getter for project ID
		assertEquals(0, project0.getProjectID());
		
		// Checks getter and setter for the name of a project
		try {
			project0.setName("new test name");
		} catch (Exception e) {
			fail("Could not change the name of a project!");
		}
		
		assertEquals("new test name", project0.getName());
		
		// Checks getter and setter for the description of a project
		try {
			project0.setDescription("new test description");
		} catch (Exception e) {
			fail("Could not change the description of a project!");
		}
		
		assertEquals("new test description", project0.getDescription());
		
		// Checks getter and setter for the creation date of a project
		try {
			project0.setCreationTime(LocalDateTime.of(2015,10,12,16,59));
		} catch (Exception e) {
			fail("Could not change the creation date of a project!");
		}
		
		assertEquals(LocalDateTime.of(2015,10,12,16,59), project0.getCreationTime());
		
		// Checks getter and setter for the due date of a project
		try {
			project0.setDueTime(LocalDateTime.of(2016,10,12,16,59));
		} catch (Exception e) {
			fail("Could not change the due date of a project!");
		}
		
		assertEquals(LocalDateTime.of(2016,10,12,16,59), project0.getDueTime());
		
		// Checks getStatus for the status of a project.
		assertEquals("ongoing", project0.getStatus());
		
		// Checks the setFinishedStatus for the status of a project
		try {
			project0.setFinishedStatus();
		} catch (Exception e) {
			fail("Could not set the status of the project to finished!");
		}
		
		// Checks the getAllTasks for the tasks of a project
		assertEquals(true, project0.getAllTasks().isEmpty());
		
		// Checks the getTaskID for a task of a project
		assertEquals(0, project0.getTaskID());
	}
}
