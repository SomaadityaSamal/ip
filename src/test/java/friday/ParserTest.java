package friday;

import friday.task.Deadline;
import friday.task.Event;
import friday.task.Task;
import friday.task.Todo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the conversion of user input and saved file lines into task objects.
 */
class ParserTest {

    /**
     * Tests that a valid todo command creates a todo with the expected save format.
     *
     * @throws FridayException if parsing fails unexpectedly
     */
    @Test
    void parseTask_validTodo_returnsTodoWithDescription() throws FridayException {
        Task task = Parser.parseTask("todo", "read book");

        assertInstanceOf(Todo.class, task);
        assertEquals("T | 0 | read book", task.toFileString());
    }

    /**
     * Tests that a valid deadline command accepts date and time input.
     *
     * @throws FridayException if parsing fails unexpectedly
     */
    @Test
    void parseTask_validDeadline_correctlyAcceptsDateAndTime() throws FridayException {
        Task task = Parser.parseTask("deadline", "return book /by 2/12/2025 1800");

        assertInstanceOf(Deadline.class, task);
        assertEquals("D | 0 | return book | 2025-12-02 1800", task.toFileString());
    }

    /**
     * Tests that missing task details cause a Friday exception.
     */
    @Test
    void parseTask_missingRequiredDetails_throwsFridayException() {
        assertThrows(FridayException.class, () -> Parser.parseTask("todo", ""));
        assertThrows(FridayException.class, () -> Parser.parseTask("deadline", "return book"));
        assertThrows(FridayException.class, () -> Parser.parseTask("event", "project meeting /from 3/12/2025 1400"));
    }
}
