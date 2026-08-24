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

            try {
                Task task = createTask(command, parts.length > 1 ? parts[1] : ""); //project meeting /from Mon 2pm /to 4pm is the 2nd para
                tasks.add(task);
                System.out.println(LINE + "\n" +
                        " Got it. I've added this task:\n" +
                        "   " + task + "\n" +
                        " Now you have " + tasks.size() + " tasks in the list.\n" +
                        LINE);
            } catch (FridayException e) {
                System.out.println(LINE + "\n" +
                        " " + e.getMessage() + "\n" +
                        LINE);
            }
        }
        System.out.println(LINE + "\n" +
                "Bye. Hope to see you again soon!" + "\n" +
                LINE);
    }

    private static Task createTask(String command, String details) throws FridayException { //insert exception portion here
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
            } else if (deadlineParts[0].isBlank()) {
                throw new FridayException("Apologies, deadline cannot have an empty description sir");
            }
            return new Deadline(deadlineParts[0], deadlineParts[1]);
        }

        if (command.equals("event")) {
            String[] eventParts = splitDetails(details, "/from");
            if (eventParts.length < 2) {
                throw new FridayException("Apologies i have no clue what that means");
            } else if (eventParts[0].isBlank()) {
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
}
