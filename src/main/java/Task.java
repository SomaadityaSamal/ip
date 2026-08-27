/**
 * Represents a task in Friday's task list.
 */
public class Task {
    protected String description;
    private boolean isDone;

    /**
     * Creates a task with the given description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the icon that shows whether this task is done.
     */
    public String getStatusIcon() {
        return this.isDone ? "X" : " ";
    }

    /**
     * Returns the icon that shows this task's type.
     */
    public String getTaskTypeIcon() {
        return " ";
    }

    /**
     * Returns this task in the format used by the save file.
     */
    public String toFileString() {
        return getTaskTypeIcon() + " | " + (this.isDone ? "1" : "0") + " | " + this.description;
    }

    @Override
    public String toString() {
        return "[" + getTaskTypeIcon() + "][" + getStatusIcon() + "] " + this.description;
    }
}
