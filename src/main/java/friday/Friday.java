package friday;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Scanner;

import friday.storage.Storage;
import friday.task.RepeatFrequency;
import friday.task.Task;
import friday.task.TaskList;

/**
 * Runs the Friday chatbot.
 */
public class Friday {
    private static final String DEFAULT_FILE_PATH = "data/duke.txt";
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_FIND = "find";
    private static final String COMMAND_HELP = "help";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_REMINDERS = "reminders";
    private static final String COMMAND_REPEAT = "repeat";
    private static final String COMMAND_SORT = "sort";
    private static final String COMMAND_UNMARK = "unmark";
    private static final long UPCOMING_REMINDER_DAYS = 2;

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
        System.out.println(getWelcome());

        while (true) {
            String rawInput = scanner.nextLine();
            assert rawInput != null : "Scanner input should not be null";

            String input = rawInput.trim();
            System.out.println(getResponse(input));
            if (isExitCommand(input)) {
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
        if (isExitCommand(trimmedInput)) {
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
        LocalDateTime now = LocalDateTime.now();
        TaskList upcomingReminders = tasks.getUpcomingReminders(now, now.plusDays(UPCOMING_REMINDER_DAYS));
        return ui.getWelcome(upcomingReminders);
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

        String normalizedCommand = command.toLowerCase(Locale.ROOT);

        if (normalizedCommand.equals(COMMAND_HELP)) {
            return ui.getHelp();
        }

        if (normalizedCommand.equals(COMMAND_LIST)) {
            return ui.getTaskList(tasks);
        }

        if (normalizedCommand.equals(COMMAND_MARK)) {
            Task task = tasks.mark(Parser.parseTaskNumber(details));
            storage.save(tasks);
            return ui.getTaskMarked(task);
        }

        if (normalizedCommand.equals(COMMAND_UNMARK)) {
            Task task = tasks.unmark(Parser.parseTaskNumber(details));
            storage.save(tasks);
            return ui.getTaskUnmarked(task);
        }

        if (normalizedCommand.equals(COMMAND_DELETE)) {
            Task removedTask = tasks.delete(Parser.parseTaskNumber(details));
            storage.save(tasks);
            return ui.getTaskDeleted(removedTask, tasks.size());
        }

        if (normalizedCommand.equals(COMMAND_FIND)) {
            TaskList matchingTasks = tasks.find(Parser.parseKeyword(details));
            return ui.getMatchingTasks(matchingTasks);
        }

        if (normalizedCommand.equals(COMMAND_SORT)) {
            tasks.sortByReminderDateTime();
            storage.save(tasks);
            return ui.getTasksSorted(tasks);
        }

        if (normalizedCommand.equals(COMMAND_REPEAT)) {
            int taskIndex = Parser.parseRepeatTaskNumber(details);
            RepeatFrequency repeatFrequency = Parser.parseRepeatFrequency(details);
            Task task = tasks.setRepeatFrequency(taskIndex, repeatFrequency);
            storage.save(tasks);
            return ui.getTaskRepeated(task, repeatFrequency);
        }

        if (normalizedCommand.equals(COMMAND_REMINDERS)) {
            return ui.getReminders(tasks.getTasksWithReminders());
        }

        Task task = Parser.parseTask(normalizedCommand, details);
        assert task != null : "Parser should return a task for add commands";
        tasks.add(task);
        storage.save(tasks);
        return ui.getTaskAdded(task, tasks.size());
    }

    private boolean isExitCommand(String input) {
        return input.equalsIgnoreCase(COMMAND_BYE);
    }
}
