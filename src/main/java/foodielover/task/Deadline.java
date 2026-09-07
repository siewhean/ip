package foodielover.task;

/**
 * Represents a deadline task that needs to be done before a specific time.
 */
public class Deadline extends Task {
    /** Deadline due date/time. */
    protected String by;

    /**
     * Constructs a new Deadline task with the specified description and due date/time.
     *
     * @param description Description of the deadline task.
     * @param by Due date/time of the deadline.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns a string representation of the deadline task.
     *
     * @return Formatted deadline task string.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
