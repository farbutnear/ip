package farquaad.ui;

import farquaad.TaskList;
import farquaad.task.Task;
import java.util.Scanner;

/**
 * Handles interactions with the user by reading input
 * and displaying output messages.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Creates a new UI instance with a scanner to read user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays a welcome message to the user.
     */
    public String displayWelcome() {
        String message = "Hello! I'm Farquaad\nWhat can I do for you?";
        System.out.println(message);
        return message;
    }

    /**
     * Displays a welcome message to the user.
     */
    public String displayGoodbye() {
        String message = "Bye. Hope to see you again soon!";
        System.out.println(message);
        return message;
    }

    /**
     * Reads the next command entered by the user.
     *
     * @return A string containing the user’s input.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to display.
     */
    public String displayError(String message) {
        System.out.println(message);
        return message;
    }

    /**
     * Displays an error message when a save file cannot be loaded.
     *
     * @param message The message describing the problem.
     */
    public String displayLoadingError(String message) {
        String fullMessage = "Something is wrong: " + message;
        System.out.println(fullMessage);
        return fullMessage;
    }

    /**
     * Displays a message confirming that a task has been added.
     *
     * @param task        The task that was added.
     * @param totalTasks  The total number of tasks after addition.
     */
    public String displayTaskAdded(Task task, int totalTasks) {
        String message = "Got it. I've added this task:\n  " + task
                + "\nNow you have " + totalTasks + " tasks in the list.";
        System.out.println(message);
        return message;
    }

    /**
     * Displays a message confirming that a task has been deleted.
     *
     * @param task        The task that was deleted.
     * @param totalTasks  The total number of tasks remaining.
     */
    public String displayTaskDeleted(Task task, int totalTasks) {
        String message = "Noted. I've removed this task:\n  " + task
                + "\nNow you have " + totalTasks + " tasks in the list.";
        System.out.println(message);
        return message;
    }

    /**
     * Displays a message confirming that a task has been marked as done.
     *
     * @param task The task that was marked as done.
     */
    public String displayTaskMarked(Task task) {
        String message = "Nice! I've marked this task as done:\n  " + task;
        System.out.println(message);
        return message;
    }

    /**
     * Displays a message confirming that a task has been unmarked.
     *
     * @param task The task that was unmarked.
     */
    public String displayTaskUnmarked(Task task) {
        String message = "OK, I've marked this task as not done yet:\n  " + task;
        System.out.println(message);
        return message;
    }

    /**
     * Displays the full list of tasks.
     *
     * @param tasks The {@code TaskList} containing tasks to display.
     */
    public String displayTaskList(TaskList tasks) {
        String message = formatTaskList("Here are the tasks in your list:", tasks);
        System.out.println(message);
        return message;
    }

    /**
     * Displays the tasks that match a given search keyword.
     *
     * @param matchingTasks The list of matching tasks.
     */
    public String displayMatchingTasks(TaskList matchingTasks) {
        String message = matchingTasks.size() == 0
                ? "No matching tasks found!"
                : formatTaskList("These are the matching tasks in your list:", matchingTasks);
        System.out.println(message);
        return message;
    }

    private String formatTaskList(String header, TaskList tasks) {
        StringBuilder sb = new StringBuilder(header + "\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }
}