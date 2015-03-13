package time;

import java.time.LocalDateTime;

/**
 * Interface for classes that need to observe time, without coupling these classes to the system
 * time.
 */
public interface TimeObserver {
    /**
     * The update method that is called by the TimeSubject, when the time changes
     */
    public void update(LocalDateTime currentTime);
}