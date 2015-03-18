package project;

import static org.junit.Assert.fail;

import java.time.LocalDateTime;

import org.junit.Test;

import data.ProjectData;

public class ProjectHandlerTest {
    @Test
    public void test() {
	fail("Not yet implemented");
	
	ProjectHandler pHandler = new ProjectHandler();
	ProjectData pData = null;

	pData.setName("test name");
	pData.setDescription("test description");
	pData.setCreationTime(LocalDateTime.now());
	pData.setDueTime(LocalDateTime.of(2016, 03, 12, 16, 59));
    }

}
