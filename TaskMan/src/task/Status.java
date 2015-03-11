package task;

/**
 * Interface for creating statuses, boolean failed() is left out because it can be deduced with the
 * two other methods
 */
public interface Status {
    public boolean ongoing();
    public boolean finished();
    public String getStatus();
}