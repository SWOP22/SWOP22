package user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class UserManagerTest {

    @Test
    public void test() {
	// Check constructor, list initialization
	UserManager userManager = new UserManager();
	assertNotEquals(null, userManager.getUsers());

	// Check getUser()
	try {
	    userManager.getUser(null);
	    fail("Accepted null argument!");
	} catch (Exception e) {
	}

	try {
	    assertEquals(null, userManager.getUser("test"));
	} catch (Exception e) {
	    fail("Did not return null!");
	}

	// Try to add a null user
	try {
	    userManager.addUser(null);
	    fail("Could add null user!");
	} catch (Exception e) {
	}

	// Create valid user
	User user = null;
	try {
	    user = new User("user1");
	} catch (Exception e) {
	    fail("Could not create valid user!");
	}
	assertNotEquals(null, user);
	assertEquals("user1", user.getName());

	// Try to add valid user to empty user list
	try {
	    userManager.addUser(user);
	} catch (Exception e) {
	    fail("Could not add valid user!");
	}

	assertEquals(1, userManager.getUsers().size());

	// Create new user with the same name
	try {
	    user = new User("user1");
	} catch (Exception e) {
	    fail("Could create valid user!");
	}

	// Try to add the user with the same name
	try {
	    userManager.addUser(user);
	    fail("Could add user with existing name!");
	} catch (Exception e) {
	}

	// Create valid user
	try {
	    user = new User("user2");
	} catch (Exception e) {
	    fail("Could not create valid user!");
	}

	assertEquals("user2", user.getName());

	// Try to add valid user to non-empty user list
	try {
	    userManager.addUser(user);
	} catch (Exception e) {
	    fail("Could not add valid user!");
	}

	assertEquals(2, userManager.getUsers().size());
	try {
	    assertEquals(user, userManager.getUser("user2"));
	} catch (Exception e) {
	    fail("Could not get user2 from UserManager!");
	}

    }

}
