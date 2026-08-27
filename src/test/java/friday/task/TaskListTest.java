package friday.task;

import friday.FridayException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests task list operations that change or expose the stored tasks.
 */
class TaskListTest {

    @Test
    void addAndSize_multipleTasks_tracksNumberOfTasks() {
        TaskList tasks = new TaskList();

        tasks.add(new Todo("read book"));
        tasks.add(new Todo("write notes"));

        assertEquals(2, tasks.size());
    }

    @Test
    void markAndUnmark_validIndex_changesDoneStateOfSelectedTask() throws FridayException {
        TaskList tasks = new TaskList();
        Todo firstTask = new Todo("read book");
        Todo secondTask = new Todo("write notes");
        tasks.add(firstTask);
        tasks.add(secondTask);

        Task markedTask = tasks.mark(1);
        assertSame(secondTask, markedTask);
        assertEquals("T | 0 | read book", firstTask.toFileString());
        assertEquals("T | 1 | write notes", secondTask.toFileString());

        Task unmarkedTask = tasks.unmark(1);
        assertSame(secondTask, unmarkedTask);
        assertEquals("T | 0 | write notes", secondTask.toFileString());
    }

    @Test
    void delete_validIndex_removesAndReturnsOnlySelectedTask() throws FridayException {
        TaskList tasks = new TaskList();
        Todo firstTask = new Todo("read book");
        Todo secondTask = new Todo("write notes");
        Todo thirdTask = new Todo("submit work");
        tasks.add(firstTask);
        tasks.add(secondTask);
        tasks.add(thirdTask);

        Task deletedTask = tasks.delete(1);

        assertSame(secondTask, deletedTask);
        assertEquals(2, tasks.size());
        assertSame(firstTask, tasks.get(0));
        assertSame(thirdTask, tasks.get(1));
    }

    @Test
    void getMarkUnmarkDelete_invalidIndexes_throwFridayException() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        assertThrows(FridayException.class, () -> tasks.get(-1));
        assertThrows(FridayException.class, () -> tasks.get(1));
        assertThrows(FridayException.class, () -> tasks.mark(2));
        assertThrows(FridayException.class, () -> tasks.unmark(2));
        assertThrows(FridayException.class, () -> tasks.delete(2));
    }

    @Test
    void asList_returnedListCannotModifyTaskList() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        List<Task> readOnlyTasks = tasks.asList();

        assertThrows(UnsupportedOperationException.class, () -> readOnlyTasks.add(new Todo("write notes")));
        assertEquals(1, tasks.size());
    }
}
