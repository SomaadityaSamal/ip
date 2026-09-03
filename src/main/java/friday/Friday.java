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
            if (input.equals("bye")) {
                break;
            }

            try {
                handleCommand(input);
            } catch (FridayException e) {
                ui.showError(e.getMessage());
            }
        }
        ui.showBye();
    }

    /**
     * Starts Friday using the default save file.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        new Friday("data/duke.txt").run();
    }

    private TaskList loadTaskList() {
        try {
            return new TaskList(storage.load());
        } catch (FridayException e) {
            ui.showLoadingError();
            return new TaskList();
        }
    }

    private void handleCommand(String input) throws FridayException {
        String command = Parser.getCommand(input);
        String details = Parser.getDetails(input);

        if (command.equals("list")) {
            ui.showTaskList(tasks);
            return;
        }

        if (command.equals("mark")) {
            Task task = tasks.mark(Parser.parseTaskNumber(details));
            storage.save(tasks);
            ui.showTaskMarked(task);
            return;
        }

        if (command.equals("unmark")) {
            Task task = tasks.unmark(Parser.parseTaskNumber(details));
            storage.save(tasks);
            ui.showTaskUnmarked(task);
            return;
        }

        if (command.equals("delete")) {
            Task removedTask = tasks.delete(Parser.parseTaskNumber(details));
            storage.save(tasks);
            ui.showTaskDeleted(removedTask, tasks.size());
            return;
        }

        if (command.equals("find")) {
            TaskList matchingTasks = tasks.find(Parser.parseKeyword(details));
            ui.showMatchingTasks(matchingTasks);
            return;
        }

        Task task = Parser.parseTask(command, details);
        tasks.add(task);
        storage.save(tasks);
        ui.showTaskAdded(task, tasks.size());
    }
}
