package task;

public class Ongoing implements Status {
    @Override
    public boolean ongoing() {
	return true;
    }

    @Override
    public boolean finished() {
	return false;
    }
}