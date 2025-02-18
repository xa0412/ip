package task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * Tests the TaskManager class.
 */
public class TaskManagerTest {
    private Path tempFile;
    private TaskManager taskManager;

    @BeforeEach
    public void setUp() throws IOException {
        tempFile = Files.createTempFile("test-tasks", ".txt");
        // Initialize TaskManager with the temp file path
        taskManager = new TaskManager(tempFile.toString());
    }

    /**
     * Tests adding a todo task to the list.
     */
    @Test
    public void testAddTodoTask() {
        taskManager.addTask("todo Read book");
        assertEquals(1, taskManager.getListArraySize());
        assertEquals("[T][ ] Read book", taskManager.getTask(0).toString());
    }

    /**
     * Tests adding a deadline task to the list.
     */
    @Test
    public void testAddDeadlineTask() {
        taskManager.addTask("deadline return book /by 2019-12-01");
        assertEquals(1, taskManager.getListArraySize());
        assertEquals("[D][ ] return book (by: Dec 01 2019)", taskManager.getTask(0).toString());
    }
    /**
     * Tests adding an event task to the list.
     */
    @Test
    public void testAddEventTask() {
        taskManager.addTask("event project meeting /from Mon 2pm /to 4pm");
        assertEquals(1, taskManager.getListArraySize());
        assertEquals("[E][ ] project meeting (from: Mon 2pm to: 4pm)", taskManager.getTask(0).toString());
    }
    /**
     * Tests deleting a task in the list.
     */
    @Test
    public void testDeleteTask() {
        taskManager.addTask("todo Read book");
        taskManager.addTask("todo Sleep");
        taskManager.deleteTask("delete 1");
        assertEquals(1, taskManager.getListArraySize());
        assertEquals("[T][ ] Sleep", taskManager.getTask(0).toString());
    }
}

