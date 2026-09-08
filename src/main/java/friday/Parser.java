package friday;

import friday.task.Deadline;
import friday.task.Event;
import friday.task.RepeatFrequency;
import friday.task.Task;
import friday.task.Todo;

/**
 * Parses user input and saved task lines.
 */
public class Parser {

    /**
     * Returns the first word of the user input as the command.
     *
     * @param input full user input
     * @return command word from the input
     */
    public static String getCommand(String input) {
        return input.split(" ", 2)[0];
    }

    /**
     * Returns everything after the command word.
     *
     * @param input full user input
     * @return details after the command word, or an empty string if there are none
     */
    public static String getDetails(String input) {
        String[] parts = input.split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }

    /**
     * Converts a one-based task number from user input to a zero-based index.
     *
     * @param details user input containing the task number
     * @return zero-based task index
     * @throws FridayException if the task number is not a valid integer
     */
    public static int parseTaskNumber(String details) throws FridayException {
        try {
            return Integer.parseInt(details.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new FridayException("Apologies, please give me a valid task number sir");
        }
    }

    /**
     * Returns the keyword to search for.
     *
     * @param details user input containing the keyword
     * @return keyword to search for
     * @throws FridayException if the keyword is blank
     */
    public static String parseKeyword(String details) throws FridayException {
        String keyword = details.trim();
        if (keyword.isBlank()) {
            throw new FridayException("Apologies, please give me a keyword to find sir");
        }
        return keyword;
    }

    /**
     * Converts the task number in a repeat command to a zero-based index.
     *
     * @param details repeat command details
     * @return zero-based task index
     * @throws FridayException if the task number is not a valid integer
     */
    public static int parseRepeatTaskNumber(String details) throws FridayException {
        return parseTaskNumber(splitRepeatDetails(details)[0]);
    }

    /**
     * Returns the repeat frequency from a repeat command.
     *
     * @param details repeat command details
     * @return repeat frequency
     * @throws FridayException if the frequency is missing or unsupported
     */
    public static RepeatFrequency parseRepeatFrequency(String details) throws FridayException {
        return RepeatFrequency.parse(splitRepeatDetails(details)[1]);
    }

    /**
     * Creates a task from the user's command and command details.
     *
     * @param command command word from the user input
     * @param details details after the command word
     * @return task created from the command
     * @throws FridayException if the command or task details are invalid
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
     *
     * @param line line from the save file
     * @return task created from the saved line
     * @throws FridayException if the saved line cannot be parsed
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
        applySavedRepeatFrequency(task, parts);
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

    private static String[] splitRepeatDetails(String details) throws FridayException {
        String[] parts = details.trim().split(" ", 2);
        if (parts.length < 2 || parts[0].isBlank() || parts[1].isBlank()) {
            throw new FridayException("Apologies, please use repeat <task number> <frequency> sir");
        }
        return parts;
    }

    private static void applySavedRepeatFrequency(Task task, String[] parts) throws FridayException {
        String repeatPrefix = "repeat:";
        String possibleRepeatFrequency = parts[parts.length - 1];
        if (possibleRepeatFrequency.startsWith(repeatPrefix)) {
            task.setRepeatFrequency(RepeatFrequency.parse(possibleRepeatFrequency.substring(repeatPrefix.length())));
        }
    }
}
