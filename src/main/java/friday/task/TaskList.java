package friday.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import friday.FridayException;

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
     *
     * @param tasks initial tasks in the task list
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task task to add
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Marks the task at the given zero-based index as done.
     *
     * @param taskIndex zero-based index of the task to mark
     * @return task that was marked
     * @throws FridayException if the index is outside the task list
     */
    public Task mark(int taskIndex) throws FridayException {
        Task task = get(taskIndex);
        task.markAsDone();
        return task;
    }

    /**
     * Marks the task at the given zero-based index as not done.
     *
     * @param taskIndex zero-based index of the task to unmark
     * @return task that was unmarked
     * @throws FridayException if the index is outside the task list
     */
    public Task unmark(int taskIndex) throws FridayException {
        Task task = get(taskIndex);
        task.markAsNotDone();
        return task;
    }

    /**
     * Deletes and returns the task at the given zero-based index.
     *
     * @param taskIndex zero-based index of the task to delete
     * @return task that was deleted
     * @throws FridayException if the index is outside the task list
     */
    public Task delete(int taskIndex) throws FridayException {
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new FridayException("Apologies, that task number is not in the list sir");
        }
        return tasks.remove(taskIndex);
    }

    /**
     * Returns the task at the given zero-based index.
     *
     * @param taskIndex zero-based index of the task to return
     * @return task at the given index
     * @throws FridayException if the index is outside the task list
     */
    public Task get(int taskIndex) throws FridayException {
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new FridayException("Apologies, that task number is not in the list sir");
        }
        return tasks.get(taskIndex);
    }

    /**
     * Returns tasks whose descriptions contain the given keyword.
     *
     * @param keyword keyword to find
     * @return task list containing matching tasks
     */
    public TaskList find(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.containsKeyword(keyword)) {
                matchingTasks.add(task);
            }
        }
        return new TaskList(matchingTasks);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return number of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the tasks as a read-only list.
     *
     * @return read-only view of the tasks
     */
    public List<Task> asList() {
        return Collections.unmodifiableList(tasks);
    }
}
