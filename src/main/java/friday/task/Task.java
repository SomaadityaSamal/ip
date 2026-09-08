package friday.task;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Represents a task in Friday's task list.
 */
public class Task {
    protected final String description;
    private boolean isDone;
    private RepeatFrequency repeatFrequency;

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
     * Sets how often this task repeats.
     *
     * @param repeatFrequency repeat frequency to set
     */
    public void setRepeatFrequency(RepeatFrequency repeatFrequency) {
        assert repeatFrequency != null : "Repeat frequency should not be null";

        this.repeatFrequency = repeatFrequency;
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
     * Returns the date and time to use when sorting or reminding about this task.
     *
     * @return empty value for tasks without a date or time
     */
    public Optional<LocalDateTime> getReminderDateTime() {
        return Optional.empty();
    }

    /**
     * Returns this task in the format used by the save file.
     *
     * @return save-file representation of the task
     */
    public String toFileString() {
        return appendRepeatFrequency(getBaseFileString());
    }

    /**
     * Returns this task's common save-file fields.
     *
     * @return common save-file representation
     */
    protected String getBaseFileString() {
        return getTaskTypeIcon() + " | " + (this.isDone ? "1" : "0") + " | " + this.description;
    }

    /**
     * Adds recurring task information to a save-file line when needed.
     *
     * @param savedTask save-file line without recurrence information
     * @return save-file line with recurrence information
     */
    protected String appendRepeatFrequency(String savedTask) {
        if (repeatFrequency == null) {
            return savedTask;
        }
        return savedTask + " | repeat:" + repeatFrequency.getText();
    }

    /**
     * Returns whether this task's description contains the keyword.
     *
     * @param keyword keyword to find
     * @return true if the description contains the keyword
     */
    public boolean containsKeyword(String keyword) {
        return this.description.contains(keyword);
    }

    /**
     * Returns this task's description.
     *
     * @return task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns this task as text for display to the user.
     *
     * @return user-facing representation of the task
     */
    @Override
    public String toString() {
        String taskText = "[" + getTaskTypeIcon() + "][" + getStatusIcon() + "] " + this.description;
        if (repeatFrequency == null) {
            return taskText;
        }
        return taskText + " (repeats: " + repeatFrequency.getText() + ")";
    }
}
