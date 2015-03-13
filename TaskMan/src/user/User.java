package user;

/**
 * Represents users who can perform tasks. This class guarantees that its user objects have a name
 * that is not empty.
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
    
    public String toString(){
    	return name;
    }
}