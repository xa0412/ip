import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {
    protected String description;
    protected boolean isDone;
    protected LocalDate dateTime;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.dateTime = null;
    }

    public Task(String description, LocalDate dateTime) {
        this.description = description;
        this.isDone = false;
        this.dateTime = dateTime;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        String dateTimeString = (dateTime != null)
                ? dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
                : "";
        return "[" + getStatusIcon() + "] " + description + (dateTime != null
                ? " (by: " + dateTimeString + ")"
                : "");
    }
}
