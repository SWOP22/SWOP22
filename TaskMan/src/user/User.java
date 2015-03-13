package user;

/**
 * Represents users who can perform tasks. This class guarantees that its user objects have a name
 * that is not empty.
 */
public class User {
    private String name;

    /**
     * Constructor for User objects.
     * 
     * @param name
     * @throws Exception
     *             if argument == null || argument.equals("")
     */
    public User(String name) throws Exception {
	if (name == null || name.equals("")) {
	    throw new Exception("Username can not be null or empty!");
	}
	this.name = name;
    }

    /**
     * @return the name of the user
     */
    public String getName() {
	return name;
    }

    /**
     * @return the name of the user
     */
    @Override
    public String toString() {
	return name;
    }
}