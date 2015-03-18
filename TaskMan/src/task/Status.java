package task;

/**
 * Interface for creating statuses
 */
public interface Status {
    public boolean ongoing();
    public boolean finished();
    public boolean failed();
    @Override
    public String toString();
}