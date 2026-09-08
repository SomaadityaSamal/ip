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
            String rawInput = scanner.nextLine();
            assert rawInput != null : "Scanner input should not be null";

            String input = rawInput.trim();
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
        assert input != null : "User input should not be null";

        String trimmedInput = input.trim();
        if (trimmedInput.equalsIgnoreCase("bye")) {
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
        assert !input.isBlank() : "Blank input should be handled before command parsing";

        String command = Parser.getCommand(input);
        String details = Parser.getDetails(input);
        assert !command.isBlank() : "Parsed command should not be blank";

        if (command.equalsIgnoreCase("help")) {
            return ui.getHelp();
        }

        if (command.equalsIgnoreCase("list")) {
            return ui.getTaskList(tasks);
        }

        if (command.equalsIgnoreCase("mark")) {
            Task task = tasks.mark(Parser.parseTaskNumber(details));
            storage.save(tasks);
            return ui.getTaskMarked(task);
        }

        if (command.equalsIgnoreCase("unmark")) {
            Task task = tasks.unmark(Parser.parseTaskNumber(details));
            storage.save(tasks);
            return ui.getTaskUnmarked(task);
        }

        if (command.equalsIgnoreCase("delete")) {
            Task removedTask = tasks.delete(Parser.parseTaskNumber(details));
            storage.save(tasks);
            return ui.getTaskDeleted(removedTask, tasks.size());
        }

        if (command.equalsIgnoreCase("find")) {
            TaskList matchingTasks = tasks.find(Parser.parseKeyword(details));
            return ui.getMatchingTasks(matchingTasks);
        }

        Task task = Parser.parseTask(command, details);
        assert task != null : "Parser should return a task for add commands";
        tasks.add(task);
        storage.save(tasks);
        return ui.getTaskAdded(task, tasks.size());
    }
}
