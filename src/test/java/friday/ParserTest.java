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

    @Test
    void parseTask_validTodo_returnsTodoWithDescription() throws FridayException {
        Task task = Parser.parseTask("todo", "read book");

        assertInstanceOf(Todo.class, task);
        assertEquals("T | 0 | read book", task.toFileString());
    }

    @Test
    void parseTask_validDeadline_acceptsUserDateFormatAndSavesFileDateFormat() throws FridayException {
        Task task = Parser.parseTask("deadline", "return book /by 2/12/2025 1800");

        assertInstanceOf(Deadline.class, task);
        assertEquals("D | 0 | return book | 2025-12-02 1800", task.toFileString());
    }

    @Test
    void parseTask_validEvent_acceptsStartAndEndTimes() throws FridayException {
        Task task = Parser.parseTask("event", "project meeting /from 3/12/2025 1400 /to 3/12/2025 1600");

        assertInstanceOf(Event.class, task);
        assertEquals("E | 0 | project meeting | 2025-12-03 1400 | 2025-12-03 1600", task.toFileString());
    }

    @Test
    void parseTask_missingRequiredDetails_throwsFridayException() {
        assertThrows(FridayException.class, () -> Parser.parseTask("todo", ""));
        assertThrows(FridayException.class, () -> Parser.parseTask("deadline", "return book"));
        assertThrows(FridayException.class, () -> Parser.parseTask("event", "project meeting /from 3/12/2025 1400"));
    }

    @Test
    void parseTask_invalidCommand_throwsFridayException() {
        assertThrows(FridayException.class, () -> Parser.parseTask("dance", "wildly"));
    }

    @Test
    void parseSavedTask_markedDeadline_restoresTaskState() throws FridayException {
        Task task = Parser.parseSavedTask("D | 1 | return book | 2025-12-02 1800");

        assertInstanceOf(Deadline.class, task);
        assertEquals("D | 1 | return book | 2025-12-02 1800", task.toFileString());
    }

    @Test
    void parseSavedTask_invalidSavedLine_throwsFridayException() {
        assertThrows(FridayException.class, () -> Parser.parseSavedTask("T | 0"));
        assertThrows(FridayException.class, () -> Parser.parseSavedTask("X | 0 | mystery task"));
        assertThrows(FridayException.class, () -> Parser.parseSavedTask("D | 0 | missing date"));
    }

    @Test
    void parseTaskNumber_validAndInvalidInputs_returnsZeroBasedIndexOrThrows() throws FridayException {
        assertEquals(0, Parser.parseTaskNumber("1"));
        assertEquals(11, Parser.parseTaskNumber(" 12 "));
        assertThrows(FridayException.class, () -> Parser.parseTaskNumber("one"));
    }
}
