package xan;

import java.time.LocalDate;
/**
 * Represents a task with a deadline. Includes a description and a due date.
 */
public class Deadline extends Task {

    /**
     * Creates a Deadline task with the specified description and due date.
     *
     * @param description Description of the task.
     * @param date        Due date for the task.
     */
    public Deadline(String description, LocalDate date) {
        super(description, date);
    }

    /**
     * Returns a string representation of the Deadline task,
     * including its type (Deadline), completion status, description, and due date.
     *
     * @return String representation of the task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString();
    }
}
