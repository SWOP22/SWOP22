package task;

/**
 * Represents the ongoing status. The ongoing status indicates finished() == false and failed() ==
 * false, not that a task has actually started.
 */
public class Ongoing implements Status {
    @Override
    public boolean ongoing() {
	return true;
    }

    @Override
    public boolean finished() {
	return false;
    }
    
    @Override
    public boolean failed() {
	return false;
    }

    @Override
    public String getStatus() {
	return "ongoing";
    }
}