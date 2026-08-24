/**
 * Represents a task stored by Friday.
 */
public class Task {
    protected String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }


    public void markAsDone() {
        this.isDone = true;
    }

  
    public void markAsNotDone() {
        this.isDone = false;
    }

 
    public String getStatusIcon() {
        return this.isDone ? "X" : " ";
    }

    public String getTaskTypeIcon() {
        return " ";
    }

    @Override
    public String toString() {
        return "[" + getTaskTypeIcon() + "][" + getStatusIcon() + "] " + this.description;
    }
}
