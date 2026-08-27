/**
 * Represents a task that needs to be done by a specific date or time.
 */
public class Deadline extends Task {
    private String by;

    /**
     * Creates a deadline task with the given description and deadline.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getTaskTypeIcon() {
        return "D";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + " | " + this.by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + this.by + ")";
    }
}
