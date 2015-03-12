package time;

import java.time.LocalDateTime;

public interface TimeObserver {
    public void update(LocalDateTime currentTime);
}