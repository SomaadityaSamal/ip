/**
 * Handles all text shown to the user.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";

    /**
     * Shows the welcome message.
     */
    public void showWelcome() {
        String banner = "_____ ____  ___ ____    _ __   __\n"
                + "|  ___|  _ \\|_ _|  _ \\  / \\\\ \\ / /\n"
                + "| |_  | |_) || || | | |/ _ \\\\ V / \n"
                + "|  _| |  _ < | || |_| / ___ \\| |  \n"
                + "|_|   |_| \\_\\___|____/_/   \\_\\_|";
        System.out.println(banner);

        System.out.println("...\n"
                + "\n"
                + "What can I do for you sir?\n"
                + LINE + "\n");
    }

    /**
     * Shows the goodbye message.
     */
    public void showBye() {
        System.out.println(LINE + "\n"
                + "Bye. Hope to see you again soon!" + "\n"
                + LINE);
    }

    /**
     * Shows the task list.
     */
    public void showTaskList(TaskList tasks) {
        System.out.println(LINE + "\n"
                + " Here are the tasks in your list:");
        for (int i = 0; i < tasks.asList().size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.asList().get(i));
        }
        System.out.println(LINE);
    }

    /**
     * Shows the message for a newly added task.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(LINE + "\n"
                + " Got it. I've added this task:\n"
                + "   " + task + "\n"
                + " Now you have " + taskCount + " tasks in the list.\n"
                + LINE);
    }

    /**
     * Shows the message for a task that has been marked as done.
     */
    public void showTaskMarked(Task task) {
        System.out.println(LINE + "\n"
                + " Nice! I've marked this task as done:\n"
                + "   " + task + "\n"
                + LINE);
    }

    /**
     * Shows the message for a task that has been marked as not done.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println(LINE + "\n"
                + " OK, I've marked this task as not done yet:\n"
                + "   " + task + "\n"
                + LINE);
    }

    /**
     * Shows the message for a deleted task.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println(LINE + "\n"
                + " Noted. I've removed this task:\n"
                + "   " + task + "\n"
                + " Now you have " + taskCount + " tasks in the list.\n"
                + LINE);
    }

    /**
     * Shows an error message.
     */
    public void showError(String message) {
        System.out.println(LINE + "\n"
                + " " + message + "\n"
                + LINE);
    }

    /**
     * Shows an error message for failed loading.
     */
    public void showLoadingError() {
        showError("Sorry, I could not load saved tasks. Starting with an empty list.");
    }
}
