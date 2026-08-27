import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that happens during a specific time period.
 */
public class Event extends Task {
    private static final DateTimeFormatter FILE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");

    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Creates an event task with the given description and time period.
     */
    public Event(String description, String from, String to) throws FridayException {
        super(description);
        this.from = parseDateTime(from);
        this.to = parseDateTime(to);
    }

    @Override
    public String getTaskTypeIcon() {
        return "E";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + " | " + this.from.format(FILE_FORMAT) + " | " + this.to.format(FILE_FORMAT);
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + this.from.format(DISPLAY_FORMAT)
                + " to: " + this.to.format(DISPLAY_FORMAT) + ")";
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
