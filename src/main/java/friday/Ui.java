package friday;

import friday.task.Task;
import friday.task.TaskList;

/**
 * Handles all text shown to the user.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";

    /**
     * Returns the welcome message.
     *
     * @return welcome message
     */
    public String getWelcome() {
        String banner = "_____ ____  ___ ____    _ __   __\n"
                         + "|  ___|  _ \\|_ _|  _ \\  / \\\\ \\ / /\n"
                         + "| |_  | |_) || || | | |/ _ \\\\ V / \n"
                         + "|  _| |  _ < | || |_| / ___ \\| |  \n"
                         + "|_|   |_| \\_\\___|____/_/   \\_\\_|";

        return banner + "\n"
                + "...\n"
                + "\n"
                + "What can I do for you sir?\n"
                + LINE;
    }

    /**
     * Shows the welcome message.
     */
    public void showWelcome() {
        System.out.println(getWelcome());
    }

    /**
     * Returns the goodbye message.
     *
     * @return goodbye message
     */
    public String getBye() {
        return LINE + "\n"
                + "Bye. Hope to see you again soon!" + "\n"
                + LINE;
    }

    /**
     * Shows the goodbye message.
     */
    public void showBye() {
        System.out.println(getBye());
    }

    /**
     * Returns the task list message.
     *
     * @param tasks tasks to show
     * @return task list message
     */
    public String getTaskList(TaskList tasks) {
        StringBuilder response = new StringBuilder(LINE)
                .append("\n")
                .append(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.asList().size(); i++) {
            response.append("\n")
                    .append(" ")
                    .append(i + 1)
                    .append(".")
                    .append(tasks.asList().get(i));
        }
        return response.append("\n")
                .append(LINE)
                .toString();
    }

    /**
     * Shows the task list.
     *
     * @param tasks tasks to show
     */
    public void showTaskList(TaskList tasks) {
        System.out.println(getTaskList(tasks));
    }

    /**
     * Returns the tasks that match a keyword search.
     *
     * @param tasks matching tasks to show
     * @return matching tasks message
     */
    public String getMatchingTasks(TaskList tasks) {
        StringBuilder response = new StringBuilder(LINE)
                .append("\n")
                .append(" Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.asList().size(); i++) {
            response.append("\n")
                    .append(" ")
                    .append(i + 1)
                    .append(".")
                    .append(tasks.asList().get(i));
        }
        return response.append("\n")
                .append(LINE)
                .toString();
    }

    /**
     * Shows the tasks that match a keyword search.
     *
     * @param tasks matching tasks to show
     */
    public void showMatchingTasks(TaskList tasks) {
        System.out.println(getMatchingTasks(tasks));
    }

    /**
     * Returns the message for a newly added task.
     *
     * @param task task that was added
     * @param taskCount number of tasks in the list
     * @return task added message
     */
    public String getTaskAdded(Task task, int taskCount) {
        return LINE + "\n"
                + " Got it. I've added this task:\n"
                + "   " + task + "\n"
                + " Now you have " + taskCount + " tasks in the list.\n"
                + LINE;
    }

    /**
     * Shows the message for a newly added task.
     *
     * @param task task that was added
     * @param taskCount number of tasks in the list
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(getTaskAdded(task, taskCount));
    }

    /**
     * Returns the message for a task that has been marked as done.
     *
     * @param task task that was marked as done
     * @return task marked message
     */
    public String getTaskMarked(Task task) {
        return LINE + "\n"
                + " Nice! I've marked this task as done:\n"
                + "   " + task + "\n"
                + LINE;
    }

    /**
     * Shows the message for a task that has been marked as done.
     *
     * @param task task that was marked as done
     */
    public void showTaskMarked(Task task) {
        System.out.println(getTaskMarked(task));
    }

    /**
     * Returns the message for a task that has been marked as not done.
     *
     * @param task task that was marked as not done
     * @return task unmarked message
     */
    public String getTaskUnmarked(Task task) {
        return LINE + "\n"
                + " OK, I've marked this task as not done yet:\n"
                + "   " + task + "\n"
                + LINE;
    }

    /**
     * Shows the message for a task that has been marked as not done.
     *
     * @param task task that was marked as not done
     */
    public void showTaskUnmarked(Task task) {
        System.out.println(getTaskUnmarked(task));
    }

    /**
     * Returns the message for a deleted task.
     *
     * @param task task that was deleted
     * @param taskCount number of tasks left in the list
     * @return task deleted message
     */
    public String getTaskDeleted(Task task, int taskCount) {
        return LINE + "\n"
                + " Noted. I've removed this task:\n"
                + "   " + task + "\n"
                + " Now you have " + taskCount + " tasks in the list.\n"
                + LINE;
    }

    /**
     * Shows the message for a deleted task.
     *
     * @param task task that was deleted
     * @param taskCount number of tasks left in the list
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println(getTaskDeleted(task, taskCount));
    }

    /**
     * Returns an error message.
     *
     * @param message error message to show
     * @return formatted error message
     */
    public String getError(String message) {
        return LINE + "\n"
                + " " + message + "\n"
                + LINE;
    }

    /**
     * Shows an error message.
     *
     * @param message error message to show
     */
    public void showError(String message) {
        System.out.println(getError(message));
    }

    /**
     * Shows an error message for failed loading.
     */
    public void showLoadingError() {
        showError("Sorry, I could not load saved tasks. Starting with an empty list.");
    }
}
