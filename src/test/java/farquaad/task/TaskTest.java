package farquaad.task;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void deadline_validDateFormats_parsesCorrectly() {
        Task.Deadline deadline1 = new Task.Deadline("Task 1", "2024-12-25");
        assertEquals("Dec 25 2024", deadline1.getDay());

        Task.Deadline deadline2 = new Task.Deadline("Task 2", "25/12/2024");
        assertEquals("Dec 25 2024", deadline2.getDay());

        Task.Deadline deadline3 = new Task.Deadline("Task 3", "25/12/2024");
        assertEquals("Dec 25 2024", deadline3.getDay());

        Task.Deadline deadline4 = new Task.Deadline("Task 4", "Dec 25 2024");
        assertEquals("Dec 25 2024", deadline4.getDay());

        Task.Deadline deadline5 = new Task.Deadline("Task 5", "25 Dec 2024");
        assertEquals("Dec 25 2024", deadline5.getDay());
    }

    @Test
    public void deadline_shortDateFormat_parsesCorrectly() {
        Task.Deadline deadline1 = new Task.Deadline("Task 1", "1/1/2024");
        assertEquals("Jan 1 2024", deadline1.getDay());

        Task.Deadline deadline2 = new Task.Deadline("Task 2", "5/3/2024");
        assertEquals("Mar 5 2024", deadline2.getDay());
    }

    @Test
    public void deadline_invalidDateFormat_preservesOriginalString() {
        Task.Deadline deadline1 = new Task.Deadline("Task 1", "invalid-date");
        assertEquals("invalid-date", deadline1.getDay());

        Task.Deadline deadline2 = new Task.Deadline("Task 2", "32/13/2024");
        assertEquals("32/13/2024", deadline2.getDay());

        Task.Deadline deadline3 = new Task.Deadline("Task 3", "2024/25/12");
        assertEquals("2024/25/12", deadline3.getDay());

        Task.Deadline deadline4 = new Task.Deadline("Task 4", "");
        assertEquals("", deadline4.getDay());
    }

    @Test
    public void deadline_edgeCaseDates_handlesCorrectly() {
        Task.Deadline deadline1 = new Task.Deadline("Task 1", "29/02/2024"); // Leap year
        assertEquals("Feb 29 2024", deadline1.getDay());

        Task.Deadline deadline2 = new Task.Deadline("Task 2", "31/01/2024"); // Last day of month
        assertEquals("Jan 31 2024", deadline2.getDay());

        Task.Deadline deadline3 = new Task.Deadline("Task 3", "01/01/2024"); // First day of year
        assertEquals("Jan 1 2024", deadline3.getDay());
    }

    @Test
    public void deadline_dateWithWhitespace_trimsAndParses() {
        Task.Deadline deadline1 = new Task.Deadline("Task 1", "  2024-12-25  ");
        assertEquals("Dec 25 2024", deadline1.getDay());

        Task.Deadline deadline2 = new Task.Deadline("Task 2", " 25/12/2024 ");
        assertEquals("Dec 25 2024", deadline2.getDay());
    }

    @Test
    public void deadline_getOriginalDay_preservesInputString() {
        String originalDate = "25/12/2024";
        Task.Deadline deadline = new Task.Deadline("Task", originalDate);
        assertEquals(originalDate, deadline.getOriginalDay());

        String invalidDate = "invalid-date";
        Task.Deadline deadline2 = new Task.Deadline("Task 2", invalidDate);
        assertEquals(invalidDate, deadline2.getOriginalDay());
    }

    @Test
    public void deadline_toStringFormat_includesFormattedDate() {
        Task.Deadline deadline = new Task.Deadline("Submit assignment", "2024-12-25");
        String result = deadline.toString();

        assertTrue(result.contains("[D]"));
        assertTrue(result.contains("Submit assignment"));
        assertTrue(result.contains("(by: Dec 25 2024)"));
        assertEquals("[D][ ] Submit assignment (by: Dec 25 2024)", result);
    }
}