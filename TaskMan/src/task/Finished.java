package task;

/**
 * Represents the finished status
 */
public class Finished implements Status {
    @Override
    public boolean ongoing() {
	return false;
    }

    @Override
    public boolean finished() {
	return true;
    }
    
    @Override
    public boolean failed() {
	return false;
    }

    @Override
    public String getStatus() {
	return "finished";
    }
}