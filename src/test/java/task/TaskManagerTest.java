package task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;



/**
 * Tests the TaskManager class.
 */
public class TaskManagerTest {
    /**
     * Tests adding a todo task to the list.
     */
    @Test
    public void testAddTodoTask() {
        TaskManager taskManager = new TaskManager();
        taskManager.addTask("todo Read a book");

        assertEquals(1, taskManager.getListArraySize());
        assertEquals("[T][ ] Read a book", taskManager.getTask(0).toString());
    }
}

