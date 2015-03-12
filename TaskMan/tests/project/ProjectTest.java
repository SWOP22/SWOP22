package project;

import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import data.ProjectData;
import task.Task;
import task.User;

public class ProjectTest {

    /**
     * This test checks whether the project constructor accepts invalid arguments
     */
    @Test
    public void testProjectConstructor() {
	// When creating invalid projects, an exception must be thrown, otherwise the test fails

	// Check invalid ID
	try {
		LocalDateTime creationTime = null;
		LocalDateTime dueTime = null;
		ProjectData pData = null;
		pData.setName("test name");
		pData.setDescription("test description");
		pData.setCreationTime(creationTime.now());
		pData.setDueTime(dueTime.of(2016,03,12,16,59));
	    Project project = new Project(-1, pData);
	    
	    fail("Project with negative ID created!");
	} catch (Exception e) {
	}

	// Check empty description
	try {
		LocalDateTime creationTime = null;
		LocalDateTime dueTime = null;
		ProjectData pData = null;
		pData.setName("test name");
		pData.setDescription("");
		pData.setCreationTime(creationTime.now());
		pData.setDueTime(dueTime.of(2016,03,12,16,59));
	    Project project = new Project(0, pData);
		
	    fail("Project with empty description created!");
	} catch (Exception e) {
	}

	// Check null description
	try {
		LocalDateTime creationTime = null;
		LocalDateTime dueTime = null;
		ProjectData pData = null;
		pData.setName("test name");
		pData.setDescription(null);
		pData.setCreationTime(creationTime.now());
		pData.setDueTime(dueTime.of(2016,03,12,16,59));
	    Project project = new Project(0, pData);
		
	    fail("Project without description created!");
	} catch (Exception e) {
	}

	// Check null creation time
	try {
		LocalDateTime creationTime = null;
		LocalDateTime dueTime = null;
		ProjectData pData = null;
		pData.setName("test name");
		pData.setDescription("test description");
		pData.setCreationTime(creationTime);
		pData.setDueTime(dueTime.of(2016,03,12,16,59));
	    Project project = new Project(0, pData);
		
	    fail("Project without creation time created!");
	} catch (Exception e) {
	}
	
	// Check null due time
		try {
			LocalDateTime creationTime = null;
			LocalDateTime dueTime = null;
			ProjectData pData = null;
			pData.setName("test name");
			pData.setDescription("test description");
			pData.setCreationTime(creationTime.now());
			pData.setDueTime(dueTime);
		    Project project = new Project(0, pData);
			
		    fail("Project without due time created!");
		} catch (Exception e) {
		}

	// Check creation time after due time
	try {
		LocalDateTime creationTime = null;
		LocalDateTime dueTime = null;
		ProjectData pData = null;
		pData.setName("test name");
		pData.setDescription("test description");
		pData.setCreationTime(creationTime.now());
		pData.setDueTime(dueTime.of(2000,03,12,16,59));
	    Project project = new Project(0, pData);

	    fail("Project created with a due time before the creation time!");
	} catch (Exception e) {
	}

	// Check two projects with same name
	try {
		LocalDateTime creationTime = null;
		LocalDateTime dueTime = null;
		ProjectData pData = null;
		pData.setName("test name");
		pData.setDescription("test description");
		pData.setCreationTime(creationTime.now());
		pData.setDueTime(dueTime.of(2016,03,12,16,59));
	    Project project1 = new Project(0, pData);
	    
		ProjectData pData2 = null;
		pData2.setName("test name");
		pData2.setDescription("new test description");
		pData2.setCreationTime(creationTime.now());
		pData2.setDueTime(dueTime.of(2016,03,12,16,59));
	    Project project2 = new Project(0, pData2);
		
	    fail("Two projects cannot have the same name!");
	} catch (Exception e) {
	}
    }
}
