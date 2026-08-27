package friday.task;

import friday.FridayException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Stores the task list and provides operations that modify it.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list with the given tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Marks the task at the given zero-based index as done.
     */
    public Task mark(int taskIndex) throws FridayException {
        Task task = get(taskIndex);
        task.markAsDone();
        return task;
    }

    /**
     * Marks the task at the given zero-based index as not done.
     */
    public Task unmark(int taskIndex) throws FridayException {
        Task task = get(taskIndex);
        task.markAsNotDone();
        return task;
    }

    /**
     * Deletes and returns the task at the given zero-based index.
     */
    public Task delete(int taskIndex) throws FridayException {
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new FridayException("Apologies, that task number is not in the list sir");
        }
        return tasks.remove(taskIndex);
    }

    /**
     * Returns the task at the given zero-based index.
     */
    public Task get(int taskIndex) throws FridayException {
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new FridayException("Apologies, that task number is not in the list sir");
        }
        return tasks.get(taskIndex);
    }

    /**
     * Returns the number of tasks in the list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the tasks as a read-only list.
     */
    public List<Task> asList() {
        return Collections.unmodifiableList(tasks);
    }
}
