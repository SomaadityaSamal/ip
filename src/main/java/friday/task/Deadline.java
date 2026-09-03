package friday.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import friday.FridayException;

/**
 * Represents a task that needs to be done by a specific date or time.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter FILE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");

    private LocalDateTime by;

    /**
     * Creates a deadline task with the given description and deadline.
     *
     * @param description description of the deadline task
     * @param by deadline date and time
     * @throws FridayException if the deadline date and time cannot be parsed
     */
    public Deadline(String description, String by) throws FridayException {
        super(description);
        this.by = parseDateTime(by);
    }

    /**
     * Returns the icon that shows this task is a deadline.
     *
     * @return deadline type icon
     */
    @Override
    public String getTaskTypeIcon() {
        return "D";
    }

    /**
     * Returns this deadline in the format used by the save file.
     *
     * @return save-file representation of the deadline
     */
    @Override
    public String toFileString() {
        return super.toFileString() + " | " + this.by.format(FILE_FORMAT);
    }

    /**
     * Returns this deadline as text for display to the user.
     *
     * @return user-facing representation of the deadline
     */
    @Override
    public String toString() {
        return super.toString() + " (by: " + this.by.format(DISPLAY_FORMAT) + ")";
    }

    private LocalDateTime parseDateTime(String dateTime) throws FridayException {
        try {
            return LocalDateTime.parse(dateTime, FILE_FORMAT);
        } catch (DateTimeParseException fileFormatException) {
            try {
                return LocalDateTime.parse(dateTime, INPUT_FORMAT);
            } catch (DateTimeParseException inputFormatException) {
                throw new FridayException("Apologies, please use the date format yyyy-MM-dd HHmm or d/M/yyyy HHmm");
            }
        }
    }
}
