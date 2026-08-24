import java.util.Scanner;
import java.util.ArrayList;


public class Friday {
    private static final String LINE = "____________________________________________________________";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Task> tasks = new ArrayList<>();
        String banner = "_____ ____  ___ ____    _ __   __\n" +
                "|  ___|  _ \\|_ _|  _ \\  / \\\\ \\ / /\n" +
                "| |_  | |_) || || | | |/ _ \\\\ V / \n" +
                "|  _| |  _ < | || |_| / ___ \\| |  \n" +
                "|_|   |_| \\_\\___|____/_/   \\_\\_|";
        System.out.println(banner);

        System.out.println("...\n" +
                "\n" +
                "What can i do for you?\n" +
                LINE + "\n"
                );

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("bye")) {
                break;
            }

            String[] parts = input.split(" ", 2);
            String command = parts[0];
            if (command.equals("mark") || command.equals("unmark")) {
                int taskIndex = Integer.parseInt(parts[1].trim()) - 1; //reindexing it to fit the array index ig

                if (command.equals("mark")) {
                    tasks.get(taskIndex).markAsDone();
                    System.out.println(LINE + "\n" +
                            " Nice! I've marked this task as done:\n" +
                            "   " + tasks.get(taskIndex) + "\n" +
                            LINE);
                } else {
                    tasks.get(taskIndex).markAsNotDone();
                    System.out.println(LINE + "\n" +
                            " OK, I've marked this task as not done yet:\n" +
                            "   " + tasks.get(taskIndex) + "\n" +
                            LINE);
                }
                continue;
            }

            if (input.equals("list")){
                System.out.println(LINE + "\n" +
                        " Here are the tasks in your list:");
                for (int i = 0 ; i < tasks.size() ; i++){
                    System.out.println(" " + (i + 1) + "." + tasks.get(i));
                }
                System.out.println(LINE);
                continue;
            }

            Task task = createTask(command, parts.length > 1 ? parts[1] : "");
            tasks.add(task);
            System.out.println(LINE + "\n" +
                    " Got it. I've added this task:\n" +
                    "   " + task + "\n" +
                    " Now you have " + tasks.size() + " tasks in the list.\n" +
                    LINE);
        }
        System.out.println(LINE + "\n" +
                "Bye. Hope to see you again soon!" + "\n" +
                LINE);
    }

    private static Task createTask(String command, String details) {
        if (command.equals("todo")) {
            return new Todo(details);
        }

        if (command.equals("deadline")) {
            String[] deadlineParts = details.split(" /by ", 2);
            return new Deadline(deadlineParts[0], deadlineParts[1]);
        }

        if (command.equals("event")) {
            String[] eventParts = details.split(" /from ", 2);
            String[] timeParts = eventParts[1].split(" /to ", 2);
            return new Event(eventParts[0], timeParts[0], timeParts[1]);
        }

        return new Todo(command + " " + details);
    }
}
