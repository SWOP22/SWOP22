package task;

/**
 * Represents the ongoing status
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
    public String getStatus() {
	return "ongoing";
    }
}