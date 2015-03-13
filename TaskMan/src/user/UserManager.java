package user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserManager {
    List<User> users;

    public UserManager() {
	users = new ArrayList<User>();
    }

    public List<User> getUsers() {
	return Collections.unmodifiableList(users);
    }

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