package friday.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import friday.FridayException;

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
     *
     * @param description description of the event task
     * @param from start date and time
     * @param to end date and time
     * @throws FridayException if the start or end date and time cannot be parsed
     */
    public Event(String description, String from, String to) throws FridayException {
        super(description);
        this.from = parseDateTime(from);
        this.to = parseDateTime(to);
    }

    /**
     * Returns the icon that shows this task is an event.
     *
     * @return event type icon
     */
    @Override
    public String getTaskTypeIcon() {
        return "E";
    }

    /**
     * Returns this event in the format used by the save file.
     *
     * @return save-file representation of the event
     */
    @Override
    public String toFileString() {
        return super.toFileString() + " | " + this.from.format(FILE_FORMAT) + " | " + this.to.format(FILE_FORMAT);
    }

    /**
     * Returns this event as text for display to the user.
     *
     * @return user-facing representation of the event
     */
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
