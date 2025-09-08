import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path file;

    public Storage(String filePath) {
        this.file = Paths.get(filePath);
    }

    private void checkFileExists() throws IOException {
        Path parent = file.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        if (!Files.exists(file)) {
            Files.createFile(file);
        }
    }

    public ArrayList<Task> load() throws IOException {
        checkFileExists();
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        ArrayList<Task> tasks = new ArrayList<>();
        for (String line : lines) {
            if (line.isBlank()) continue;
            String[] p = line.split("\\s*\\|\\s*");
            String taskType = p[0];
            boolean isDone = "1".equals(p[1]);
            switch (taskType) {
                case "T": {
                    Task t = new Task.ToDo(p[2]);
                    if (isDone) t.markAsDone();
                    tasks.add(t);
                    break;
                }
                case "D": {
                    Task t = new Task.Deadline(p[2], p[3]);
                    if (isDone) t.markAsDone();
                    tasks.add(t);
                    break;
                }
                case "E": {
                    Task t = new Task.Event(p[2], p[3], p[4]);
                    if (isDone) t.markAsDone();
                    tasks.add(t);
                    break;
                }
                default:
                    break;
            }
        }
        return tasks;
    }

    public void save(List<Task> tasks) throws IOException {
        checkFileExists();
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            for (Task t : tasks) {
                String done = t.getIsDone() ? "1" : "0";
                if (t instanceof Task.ToDo) {
                    Task.ToDo td = (Task.ToDo) t;
                    writer.write(String.join(" | ", "T", done, td.getDescription()));
                } else if (t instanceof Task.Deadline) {
                    Task.Deadline d = (Task.Deadline) t;
                    writer.write(String.join(" | ", "D", done, d.getDescription(), d.getOriginalDay()));
                } else if (t instanceof Task.Event) {
                    Task.Event e = (Task.Event) t;
                    writer.write(
                            String.join(" | ", "E",
                                    done, e.getDescription(), e.getOriginalStart(), e.getOriginalEnd()));
                }
                writer.newLine();
            }
        }
    }

}
