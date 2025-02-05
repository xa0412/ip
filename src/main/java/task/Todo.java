package xan;

/**
 * Represents a simple task with a description
 */
public class Todo extends Task {

    /**
     * Creates a Todo task with the given description.
     *
     * @param description The task's description.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the Todo task, including its type,
     * status, and description.
     *
     * @return A string representation of the Todo task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
