package time;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The abstract TimeSubject class keeps a list of TimeObservers who will be notified when the time
 * changes. Gets extended by the SystemTime class.
 */
public abstract class TimeSubject {
    List<TimeObserver> timeObservers = new ArrayList<TimeObserver>();

    /**
     * Adds a time observer to the observer list
     * 
     * @param timeObserver
     * @throws Exception
     *             if argument == null
     */
    public void addTimeObserver(TimeObserver timeObserver) throws Exception {
	if (timeObserver == null) {
	    throw new Exception("Can not add null as a time observer!");
	}
	timeObservers.add(timeObserver);
    }

    /**
     * Removes the given TimeObserver if it exists
     * 
     * @param timeObserver
     */
    public void removeTimeObserver(TimeObserver timeObserver) {
	timeObservers.remove(timeObserver);
    }

    /**
     * Notifies all the TimeObservers by calling their update method with the new time as an
     * argument
     */
    public void notifyTimeObservers(LocalDateTime currentTime) {
	for (TimeObserver timeObserver : timeObservers) {
	    timeObserver.update(currentTime);
	}
    }
}