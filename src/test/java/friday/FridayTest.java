package friday;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests the response method used by the GUI.
 */
class FridayTest {
    @TempDir
    private Path tempDir;

    @Test
    void getResponse_addTaskThenList_returnsExpectedMessages() {
        Friday friday = new Friday(tempDir.resolve("duke.txt").toString());

        String addResponse = friday.getResponse("todo read book");
        String listResponse = friday.getResponse("list");

        assertTrue(addResponse.contains("Got it. I've added this task:"));
        assertTrue(addResponse.contains("[T][ ] read book"));
        assertTrue(listResponse.contains("1.[T][ ] read book"));
    }

    @Test
    void getResponse_bye_returnsGoodbyeMessage() {
        Friday friday = new Friday(tempDir.resolve("duke.txt").toString());

        String response = friday.getResponse("bye");

        assertTrue(response.contains("Bye. Hope to see you again soon!"));
    }
}
