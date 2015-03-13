package task;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class UserTest {

    @Test
    public void test() {
	User user = null;

	try {
	    user = new User(null);
	    fail("Null accepted as username!");
	} catch (Exception e) {
	}
	try {
	    user = new User("");
	    fail("Empty username accepted!");
	} catch (Exception e) {
	}
	try {
	    user = new User("TestUser");
	} catch (Exception e) {
	    fail("Object initialization with valid argument failed!");
	}

	if (user != null) {
	    assertEquals("TestUser", user.getName());
	}
    }
}
