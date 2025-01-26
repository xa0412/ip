package Xan;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {
    @Test
    public void testAddTodoTask() {
        TaskManager taskManager = new TaskManager();
        taskManager.addList("todo Read a book");

        assertEquals(1, taskManager.getListArraySize());
        assertEquals("[T][ ] Read a book", taskManager.getTask(0).toString());
    }

    @Test
    public void testAddEventTask() {
        TaskManager taskManager = new TaskManager();
        taskManager.addList("event Attend meeting /from 10am /to 11am");

        assertEquals(1, taskManager.getListArraySize());
        assertEquals("[E][ ] Attend meeting (from: 10am to: 11am)", taskManager.getTask(0).toString());
    }
}

