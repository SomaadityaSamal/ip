import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads tasks from and saves tasks to the hard disk.
 */
public class Storage {
    private final Path filePath;

    /**
     * Creates a storage object that uses the given file path.
     */
    public Storage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the save file.
     */
    public ArrayList<Task> load() throws FridayException {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return tasks;
        }

        try {
            for (String line : Files.readAllLines(filePath, StandardCharsets.UTF_8)) {
                if (!line.isBlank()) {
                    tasks.add(Parser.parseSavedTask(line));
                }
            }
            return tasks;
        } catch (IOException e) {
            throw new FridayException("Sorry, I could not load saved tasks. Starting with an empty list.");
        }
    }

    /**
     * Saves all tasks to the save file.
     */
    public void save(TaskList tasks) throws FridayException {
        try {
            Files.createDirectories(filePath.getParent());
            List<String> lines = new ArrayList<>();
            for (Task task : tasks.asList()) {
                lines.add(task.toFileString());
            }
            Files.write(filePath, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FridayException("Sorry, I could not save your tasks to the hard disk.");
        }
    }
}
