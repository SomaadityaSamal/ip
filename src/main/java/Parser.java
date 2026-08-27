/**
 * Parses user input and saved task lines.
 */
public class Parser {

    /**
     * Returns the first word of the user input as the command.
     */
    public static String getCommand(String input) {
        return input.split(" ", 2)[0];
    }

    /**
     * Returns everything after the command word.
     */
    public static String getDetails(String input) {
        String[] parts = input.split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }

    /**
     * Converts a one-based task number from user input to a zero-based index.
     */
    public static int parseTaskNumber(String details) throws FridayException {
        try {
            return Integer.parseInt(details.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new FridayException("Apologies, please give me a valid task number sir");
        }
    }

    /**
     * Creates a task from the user's command and command details.
     */
    public static Task parseTask(String command, String details) throws FridayException {
        if (command.equals("todo")) {
            return parseTodo(details);
        }

        if (command.equals("deadline")) {
            return parseDeadline(details);
        }

        if (command.equals("event")) {
            return parseEvent(details);
        }

        throw new FridayException("Apologies i have no clue what that means");
    }

    /**
     * Creates a task from one line in the save file.
     */
    public static Task parseSavedTask(String line) throws FridayException {
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

    private static Task parseTodo(String details) throws FridayException {
        if (details.isBlank()) {
            throw new FridayException("Apologies, todo cannot have an empty description sir");
        }
        return new Todo(details);
    }

    private static Task parseDeadline(String details) throws FridayException {
        String[] deadlineParts = splitDetails(details, "/by");
        if (deadlineParts.length < 2) {
            throw new FridayException("Apologies i have no clue what that means");
        }
        if (deadlineParts[0].isBlank()) {
            throw new FridayException("Apologies, deadline cannot have an empty description sir");
        }
        return new Deadline(deadlineParts[0], deadlineParts[1]);
    }

    private static Task parseEvent(String details) throws FridayException {
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
