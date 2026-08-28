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

    /**
     * Tests that adding multiple tasks updates the task count.
     */
    @Test
    void addAndSize_multipleTasks_tracksNumberOfTasks() {
        TaskList tasks = new TaskList();

        tasks.add(new Todo("read book"));
        tasks.add(new Todo("write notes"));

        assertEquals(2, tasks.size());
    }

    /**
     * Tests that deleting a task removes and returns only the selected task.
     *
     * @throws FridayException if task access fails unexpectedly
     */
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

    /**
     * Tests that the list returned by asList cannot modify the task list.
     */
    @Test
    void asList_returnedListCannotModifyTaskList() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        List<Task> readOnlyTasks = tasks.asList();

        assertThrows(UnsupportedOperationException.class, () -> readOnlyTasks.add(new Todo("write notes")));
        assertEquals(1, tasks.size());
    }
}
