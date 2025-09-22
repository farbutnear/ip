package farquaad.task;

import farquaad.task.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void deadlineStoresDatesCorrectly() {
        Task.Deadline d = new Task.Deadline("submit report", "2025-09-25");

        // Check the stored ISO date
        assertEquals("2025-09-25", d.getIsoDay());

        // Check pretty-print toString()
        String str = d.toString();
        assertTrue(str.contains("submit report"));
        assertTrue(str.contains("25 Sep 2025"));
    }

    @Test
    public void eventStoresDatesCorrectly() {
        Task.Event e = new Task.Event("conference",
                "2025-09-23 0000", "2025-09-29 2359");

        // Check original values
        assertEquals("2025-09-23 0000", e.getOriginalStart());
        assertEquals("2025-09-29 2359", e.getOriginalEnd());

        // Check formatted values
        assertTrue(e.getStart().contains("Sep"));
        assertTrue(e.getEnd().contains("Sep"));
    }

    @Test
    public void todoStoresDescriptionCorrectly() {
        Task.ToDo todo = new Task.ToDo("read book");

        assertEquals("read book", todo.getDescription());
        assertFalse(todo.getIsDone());

        todo.markAsDone();
        assertTrue(todo.getIsDone());
    }
}
