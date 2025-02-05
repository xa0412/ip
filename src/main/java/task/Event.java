package task;

/**
 * A task representing an event with a description, start time, and end time.
 */
public class Event extends Task {
    private final String startTime;
    private final String endTime;

    /**
     * Creates an Event task with a description, start time, and end time.
     *
     * @param description The task's description.
     * @param startTime The event's start time.
     * @param endTime The event's end time.
     */
    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns a string representation of the event, including its type, status,
     * description, start time, and end time.
     *
     * @return A string representation of the Event task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startTime + " to: " + endTime + ")";
    }
}
