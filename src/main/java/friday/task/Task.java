package friday.task;

/**
 * Represents a task in Friday's task list.
 */
public class Task {
    protected String description;
    private boolean isDone;

    /**
     * Creates a task with the given description.
     *
     * @param description description of the task
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
     *
     * @return status icon of the task
     */
    public String getStatusIcon() {
        return this.isDone ? "X" : " ";
    }

    /**
     * Returns the icon that shows this task's type.
     *
     * @return type icon of the task
     */
    public String getTaskTypeIcon() {
        return " ";
    }

    /**
     * Returns this task in the format used by the save file.
     *
     * @return save-file representation of the task
     */
    public String toFileString() {
        return getTaskTypeIcon() + " | " + (this.isDone ? "1" : "0") + " | " + this.description;
    }

    /**
     * Returns this task as text for display to the user.
     *
     * @return user-facing representation of the task
     */
    @Override
    public String toString() {
        return "[" + getTaskTypeIcon() + "][" + getStatusIcon() + "] " + this.description;
    }
}
