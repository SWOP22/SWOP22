package task;

public class Finished implements Status {
    @Override
    public boolean ongoing() {
	return false;
    }

    @Override
    public boolean finished() {
	return true;
    }
}