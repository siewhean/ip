package foodielover.task;

/**
 * Represents an event task that occurs within a specific time period.
 */
public class Event extends Task {
    /** Start date/time of the event. */
    protected String from;

    /** End date/time of the event. */
    protected String to;

    /**
     * Constructs a new Event task with the specified description and start/end time.
     *
     * @param description Description of the event task.
     * @param from Start date/time of the event.
     * @param to End date/time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of the event task.
     *
     * @return Formatted event task string.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
