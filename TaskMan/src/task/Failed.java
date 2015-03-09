package task;

public class Failed implements Status {
    @Override
    public boolean ongoing() {
	return false;
    }

    @Override
    public boolean finished() {
	return false;
    }
}