/**
 * Represents a task with a description and a completion status.
 */
public class Task {
    /** Description of the task. */
    protected String description;

    /** Indicates whether the task is completed. */
    protected boolean isDone;

    /**
     * Constructs a new Task with the specified description.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon representing task completion.
     *
     * @return "X" if completed, otherwise " ".
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done yet.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Returns the description of the task.
     *
     * @return Task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a string representation of the task with its status and description.
     *
     * @return Formatted task string.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
