/**
 * Represents a task that happens during a specific time period.
 */
public class Event extends Task {
    private String from;
    private String to;

    /**
     * Creates an event task with the given description and time period.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getTaskTypeIcon() {
        return "E";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + " | " + this.from + " | " + this.to;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}
