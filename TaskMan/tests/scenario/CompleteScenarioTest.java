package scenario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;

import main.FrontController;

import org.junit.Test;

import project.Project;
import task.Task;
import data.ProjectData;
import data.TaskData;
import exceptions.InvalidProjectDataException;

public class CompleteScenarioTest {

    @Test
    public void test() {
	// Initialize system with a time object
	LocalDateTime time = LocalDateTime.of(2014, 1, 1, 8, 0);
	FrontController frontController = null;

	try {
	    frontController = new FrontController(time);
	} catch (Exception e) {
	    fail("Could not create FrontController!");
	}

	assertNotEquals(null, frontController);

	// Create a test project
	ProjectData projectData = frontController.getProjectData();
	projectData.setName("Test project");
	projectData.setDescription("A project for testing the create task use case.");
	projectData.setCreationTime(time);
	projectData.setDueTime(time.plusDays(7));

	assertEquals(projectData.getName(), "Test project");
	assertEquals(projectData.getDescription(), "A project for testing the create task use case.");
	assertEquals(projectData.getCreationTime(), time);
	assertEquals(projectData.getDueTime(), time.plusDays(7));

	try {
	    frontController.createProject(projectData);
	} catch (InvalidProjectDataException e) {
	    fail("Could not create a valid project!");
	}

	assertEquals(frontController.getProjects().get(0).getName(), projectData.getName());
	assertEquals(frontController.getProjects().get(0).getDescription(), projectData.getDescription());
	assertEquals(frontController.getProjects().get(0).getCreationTime(), projectData.getCreationTime());
	assertEquals(frontController.getProjects().get(0).getDueTime(), projectData.getDueTime());

	Task task = null;

	try {
	    frontController.createTask(null);
	    fail("Could supply null in stead of a valid TaskData object!");
	} catch (Exception e) {
	}
	
	// TODO!

	/*TaskData taskData = new TaskData(project);

	frontController.getTaskData(project);

	try {
	    // task = new Task(taskData);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}*/

    }
}
