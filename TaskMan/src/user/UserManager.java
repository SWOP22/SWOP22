package user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to manage the users of this system.
 */
public class UserManager {
    List<User> users;

    /**
     * Constructor for the UserManager class.
     */
    public UserManager() {
	users = new ArrayList<User>();
	addTeam();
    }
    
    /**
     * Add our names to the user list;
     */
    private void addTeam(){
    	try {
			users.add(new User("Dries"));
			users.add(new User("Joren"));
			users.add(new User("Mika"));
			users.add(new User("Vincent"));
		} catch (Exception e) {
			//Can never fail.
			e.printStackTrace();
		}
    }
    
    /**
     * @return a read-only list with all the users of this system
     */
    public List<User> getUsers() {
	return Collections.unmodifiableList(users);
    }

    /**
     * @param Username
     * @return user with the given username if it exists, null otherwise
     * @throws Exception
     *             if given username == null
     */
    public User getUser(String Username) throws Exception {
	if (Username == null) {
	    throw new Exception("Can not search for a user with a null object username!");
	}
	for (User user : users) {
	    if (user.getName().equals(Username)) {
		return user;
	    }
	}
	return null;
    }

    /**
     * Adds a user to the user list.
     * 
     * @param user
     * @throws Exception
     *             if given user == null or if the name of the given user already exists
     */
    public void addUser(User user) throws Exception {
	if (user == null) {
	    throw new Exception("Can not add null as a user!");
	}
	for (User existingUser : users) {
	    if (user.getName().equals(existingUser.getName())) {
		throw new Exception("Can not add user, because its username is already taken!");
	    }
	}
	users.add(user);
    }
}