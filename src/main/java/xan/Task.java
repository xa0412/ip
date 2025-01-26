package xan;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a description and a completion status.
 * Serves as a base class for specific task types like Todo, Deadline, and Event.
 */
public class Task {
    protected final String description;
    protected boolean isDone;
    protected final LocalDate date;

    /**
     * Creates a task with the given description.
     *
     * @param description The task's description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.date = null;
    }

    /**
     * Creates a task with the given description and date.
     *
     * @param description The task's description.
     * @param date The associated date for the task (e.g., deadline or event time).
     */
    public Task(String description, LocalDate date) {
        this.description = description;
        this.isDone = false;
        this.date = date;
    }

    /**
     * Returns the status icon of the task.
     * The icon is "X" if the task is marked as done,
     * otherwise it is a space (" ").
     * @return A string representing the status icon of the task.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the description of the task.
     *
     * @return The task's description as a string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a string representation of the task, including its status and description.
     *
     * @return A string representation of the task.
     */
    @Override
    public String toString() {
        String dateTimeString = (date != null)
                ? date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
                : "";
        return "[" + getStatusIcon() + "] " + description + (date != null
                ? " (by: " + dateTimeString + ")"
                : "");
    }
}
