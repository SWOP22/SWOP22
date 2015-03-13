package task;

import static org.junit.Assert.*;

import org.junit.Test;

public class StatusTest {

    @Test
    public void test() {
	Status status = new Ongoing();
	assertEquals(true, status.ongoing());
	assertEquals(false, status.finished());
	assertEquals(false, status.failed());
	assertEquals("ongoing", status.getStatus());
	
	status = new Finished();
	assertEquals(false, status.ongoing());
	assertEquals(true, status.finished());
	assertEquals(false, status.failed());
	assertEquals("finished", status.getStatus());
	
	status = new Failed();
	assertEquals(false, status.ongoing());
	assertEquals(false, status.finished());
	assertEquals(true, status.failed());
	assertEquals("failed", status.getStatus());
    }

}
