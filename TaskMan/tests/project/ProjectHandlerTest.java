package project;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import time.TimeSubject;
import data.ProjectData;

public class ProjectHandlerTest {
	@Test
	public void test() {
		ProjectHandler pHandler = new ProjectHandler();
		ProjectData pData = null;
		TimeSubject timeSubject = null;
		
		pData.setName("test name");
		pData.setDescription("test description");
		pData.setCreationTime(LocalDateTime.now());
		pData.setDueTime(LocalDateTime.of(2016,03,12,16,59));
	}

}
