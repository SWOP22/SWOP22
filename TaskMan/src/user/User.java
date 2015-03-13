package user;

/**
 * Represents users who can perform tasks
 */
public class User {
    private String name;

    public User(String name) throws Exception {
	if (name == null || name.equals("")) {
	    throw new Exception("Username can not be null or empty!");
	}
	this.name = name;
    }

    public String getName() {
	return this.name;
    }
}