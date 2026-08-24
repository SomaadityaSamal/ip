/**
 * Represents a task without a date or time attached to it.
 */
public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    @Override
    public String getTaskTypeIcon() {
        return "T";
    }
}
