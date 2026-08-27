import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Runs the Friday chatbot and stores the user's task list on the hard disk.
 */
public class Friday {
    private static final String LINE = "____________________________________________________________";
    private static final Path DATA_FILE = Path.of("data", "duke.txt");

    /**
     * Starts the chatbot, loads saved tasks, and handles user commands.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Task> tasks = loadTasks();
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

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("bye")) {
                break;
            }

            String[] parts = input.split(" ", 2);
            String command = parts[0];
            if (command.equals("mark") || command.equals("unmark")) {
                int taskIndex = Integer.parseInt(parts[1].trim()) - 1;

                if (command.equals("mark")) {
                    tasks.get(taskIndex).markAsDone();
                    saveTasks(tasks);
                    System.out.println(LINE + "\n"
                            + " Nice! I've marked this task as done:\n"
                            + "   " + tasks.get(taskIndex) + "\n"
                            + LINE);
                } else {
                    tasks.get(taskIndex).markAsNotDone();
                    saveTasks(tasks);
                    System.out.println(LINE + "\n"
                            + " OK, I've marked this task as not done yet:\n"
                            + "   " + tasks.get(taskIndex) + "\n"
                            + LINE);
                }
                continue;
            }

            if (command.equals("delete")) {
                int taskIndex = Integer.parseInt(parts[1].trim()) - 1;
                Task removedTask = tasks.remove(taskIndex);
                saveTasks(tasks);
                System.out.println(LINE + "\n"
                        + " Noted. I've removed this task:\n"
                        + "   " + removedTask + "\n"
                        + " Now you have " + tasks.size() + " tasks in the list.\n"
                        + LINE);
                continue;
            }

            if (input.equals("list")) {
                System.out.println(LINE + "\n"
                        + " Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(" " + (i + 1) + "." + tasks.get(i));
                }
                System.out.println(LINE);
                continue;
            }

            try {
                Task task = createTask(command, parts.length > 1 ? parts[1] : "");
                tasks.add(task);
                saveTasks(tasks);
                System.out.println(LINE + "\n"
                        + " Got it. I've added this task:\n"
                        + "   " + task + "\n"
                        + " Now you have " + tasks.size() + " tasks in the list.\n"
                        + LINE);
            } catch (FridayException e) {
                System.out.println(LINE + "\n"
                        + " " + e.getMessage() + "\n"
                        + LINE);
            }
        }
        System.out.println(LINE + "\n"
                + "Bye. Hope to see you again soon!" + "\n"
                + LINE);
    }

    private static Task createTask(String command, String details) throws FridayException {
        if (command.equals("todo")) {
            if (details.isBlank()) {
                throw new FridayException("Apologies, todo cannot have an empty description sir");
            }
            return new Todo(details);
        }

        if (command.equals("deadline")) {
            String[] deadlineParts = splitDetails(details, "/by");
            if (deadlineParts.length < 2) {
                throw new FridayException("Apologies i have no clue what that means");
            }
            if (deadlineParts[0].isBlank()) {
                throw new FridayException("Apologies, deadline cannot have an empty description sir");
            }
            return new Deadline(deadlineParts[0], deadlineParts[1]);
        }

        if (command.equals("event")) {
            String[] eventParts = splitDetails(details, "/from");
            if (eventParts.length < 2) {
                throw new FridayException("Apologies i have no clue what that means");
            }
            if (eventParts[0].isBlank()) {
                throw new FridayException("Apologies, event cannot have an empty description sir");
            }
            String[] timeParts = splitDetails(eventParts[1], "/to");
            if (timeParts.length < 2) {
                throw new FridayException("Apologies i have no clue what that means");
            }
            return new Event(eventParts[0], timeParts[0], timeParts[1]);
        }

        throw new FridayException("Apologies i have no clue what that means");
    }

    private static String[] splitDetails(String details, String marker) {
        int markerIndex = details.indexOf(marker);
        if (markerIndex < 0) {
            return new String[] { details };
        }

        String description = details.substring(0, markerIndex).trim();
        String dateOrTime = details.substring(markerIndex + marker.length()).trim();
        return new String[] { description, dateOrTime };
    }

    private static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!Files.exists(DATA_FILE)) {
            return tasks;
        }

        try {
            for (String line : Files.readAllLines(DATA_FILE, StandardCharsets.UTF_8)) {
                if (!line.isBlank()) {
                    tasks.add(parseTask(line));
                }
            }
        } catch (IOException | FridayException e) {
            System.out.println(LINE + "\n"
                    + " Sorry, I could not load saved tasks. Starting with an empty list.\n"
                    + LINE);
            return new ArrayList<>();
        }
        return tasks;
    }

    private static void saveTasks(List<Task> tasks) {
        try {
            Files.createDirectories(DATA_FILE.getParent());
            List<String> lines = new ArrayList<>();
            for (Task task : tasks) {
                lines.add(task.toFileString());
            }
            Files.write(DATA_FILE, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println(LINE + "\n"
                    + " Sorry, I could not save your tasks to the hard disk.\n"
                    + LINE);
        }
    }

    private static Task parseTask(String line) throws FridayException {
        String[] parts = line.split(" \\| ", -1);
        if (parts.length < 3) {
            throw new FridayException("Invalid saved task format");
        }

        Task task;
        String taskType = parts[0];
        String description = parts[2];
        if (taskType.equals("T")) {
            task = new Todo(description);
        } else if (taskType.equals("D") && parts.length >= 4) {
            task = new Deadline(description, parts[3]);
        } else if (taskType.equals("E") && parts.length >= 5) {
            task = new Event(description, parts[3], parts[4]);
        } else {
            throw new FridayException("Invalid saved task type");
        }

        if (parts[1].equals("1")) {
            task.markAsDone();
        }
        return task;
    }
}
