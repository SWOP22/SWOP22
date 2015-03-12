package time;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class TimeSubject {
    List<TimeObserver> timeObservers = new ArrayList<TimeObserver>();
    
    public void addTimeObserver(TimeObserver timeObserver) throws Exception {
	if (timeObserver == null) {
	    throw new Exception("Can not add null as a time observer!");
	}
	timeObservers.add(timeObserver);
    }
    
    public void removeTimeObserver(TimeObserver timeObserver) {
	timeObservers.remove(timeObserver);
    }
    
    public void notifyTimeObservers(LocalDateTime currentTime) {
	for (TimeObserver timeObserver : timeObservers) {
	    timeObserver.update(currentTime);
	}
    }
}