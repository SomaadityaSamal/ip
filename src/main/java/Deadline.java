import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
     */
    public Deadline(String description, String by) throws FridayException {
        super(description);
        this.by = parseDateTime(by);
    }

    @Override
    public String getTaskTypeIcon() {
        return "D";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + " | " + this.by.format(FILE_FORMAT);
    }

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
