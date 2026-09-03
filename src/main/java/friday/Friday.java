package friday;

import java.nio.file.Path;
import java.util.Scanner;

import friday.storage.Storage;
import friday.task.Task;
import friday.task.TaskList;

/**
 * Runs the Friday chatbot.
 */
public class Friday {
    private static final String DEFAULT_FILE_PATH = "data/duke.txt";

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Creates a Friday chatbot that stores tasks at the given file path.
     *
     * @param filePath path of the file used to save tasks
     */
    public Friday(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(Path.of(filePath));
        this.tasks = loadTaskList();
    }

    /**
     * Starts the chatbot and handles user commands until the user says bye.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        ui.showWelcome();

        while (true) {
            String input = scanner.nextLine().trim();
            System.out.println(getResponse(input));
            if (input.equals("bye")) {
                break;
            }
        }
    }

    /**
     * Starts Friday using the default save file.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        new Friday(DEFAULT_FILE_PATH).run();
    }

    /**
     * Returns Friday's response to one line of user input.
     *
     * @param input user input from the command line or GUI
     * @return response to show to the user
     */
    public String getResponse(String input) {
        String trimmedInput = input.trim();
        if (trimmedInput.equals("bye")) {
            return ui.getBye();
        }

        try {
            return handleCommand(trimmedInput);
        } catch (FridayException e) {
            return ui.getError(e.getMessage());
        }
    }

    /**
     * Returns Friday's welcome message.
     *
     * @return welcome message to show when the app starts
     */
    public String getWelcome() {
        return ui.getWelcome();
    }

    private TaskList loadTaskList() {
        try {
            return new TaskList(storage.load());
        } catch (FridayException e) {
            ui.showLoadingError();
            return new TaskList();
        }
    }

    private String handleCommand(String input) throws FridayException {
        String command = Parser.getCommand(input);
        String details = Parser.getDetails(input);

        if (command.equals("list")) {
            return ui.getTaskList(tasks);
        }

        if (command.equals("mark")) {
            Task task = tasks.mark(Parser.parseTaskNumber(details));
            storage.save(tasks);
            return ui.getTaskMarked(task);
        }

        if (command.equals("unmark")) {
            Task task = tasks.unmark(Parser.parseTaskNumber(details));
            storage.save(tasks);
            return ui.getTaskUnmarked(task);
        }

        if (command.equals("delete")) {
            Task removedTask = tasks.delete(Parser.parseTaskNumber(details));
            storage.save(tasks);
            return ui.getTaskDeleted(removedTask, tasks.size());
        }

        if (command.equals("find")) {
            TaskList matchingTasks = tasks.find(Parser.parseKeyword(details));
            return ui.getMatchingTasks(matchingTasks);
        }

        Task task = Parser.parseTask(command, details);
        tasks.add(task);
        storage.save(tasks);
        return ui.getTaskAdded(task, tasks.size());
    }
}
