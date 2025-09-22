package farquaad.command;

import farquaad.storage.Storage;
import farquaad.task.Task;
import farquaad.TaskList;
import farquaad.ui.Ui;

/**
 * Represents a command that finds and lists tasks
 * containing a given keyword in their description.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Creates a {@code FindCommand} with the specified search keyword.
     *
     * @param keyword The keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword.trim();
    }

    /**
     * Executes the find command by searching through the task list
     * for descriptions containing the keyword, and displaying
     * the matching tasks through the UI.
     *
     * @param tasks   The task list to search in.
     * @param ui      The UI used to display the matching tasks.
     * @param storage The storage handler (unused for find).
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        TaskList matching = new TaskList();
        for (Task task : tasks.getTasks()) {
            if (task.getDescription().contains(keyword)) {
                matching.add(task);
            }
        }
        return ui.displayMatchingTasks(matching);
    }
}