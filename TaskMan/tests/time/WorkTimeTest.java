package time;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;

public class WorkTimeTest {

    @Test
    public void test() {
	// Check if returns null if start time = null
	assertEquals(null, WorkTime.getEstimatedEndTime(null, 10));

	// Check start before 8u
	assertEquals(LocalDateTime.of(2015, 1, 1, 8, 10),
		WorkTime.getEstimatedEndTime(LocalDateTime.of(2015, 1, 1, 7, 30), 10));

	// Check start after 16u
	assertEquals(LocalDateTime.of(2015, 1, 2, 8, 10),
		WorkTime.getEstimatedEndTime(LocalDateTime.of(2015, 1, 1, 17, 50), 10));

	// Check start after 16u on a friday
	assertEquals(LocalDateTime.of(2015, 1, 5, 8, 10),
		WorkTime.getEstimatedEndTime(LocalDateTime.of(2015, 1, 2, 16, 30), 10));

	// Check start on a saturday
	assertEquals(LocalDateTime.of(2015, 1, 5, 8, 10),
		WorkTime.getEstimatedEndTime(LocalDateTime.of(2015, 1, 3, 8, 30), 10));

	// Check start on a sunday
	assertEquals(LocalDateTime.of(2015, 1, 5, 8, 10),
		WorkTime.getEstimatedEndTime(LocalDateTime.of(2015, 1, 4, 8, 30), 10));
    }

}
