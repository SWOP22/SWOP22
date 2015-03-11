package task;

/**
 * Represents the failed status
 */
public class Failed implements Status {
    @Override
    public boolean ongoing() {
	return false;
    }

    @Override
    public boolean finished() {
	return false;
    }

    @Override
    public String getStatus() {
	return "failed";
    }
}