package farquaad.ui;

import farquaad.TaskList;
import farquaad.task.Task;
import java.util.Scanner;

/**
 * Handles interactions with the user by reading input
 * and displaying output messages.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Creates a new UI instance with a scanner to read user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays a welcome message to the user.
     */
    public void displayWelcome() {
        System.out.println("Hello! I'm Farquaad");
        System.out.println("What can I do for you?");
    }

    /**
     * Displays a welcome message to the user.
     */
    public void displayGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
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
    public void displayError(String message) {
        System.out.println(message);
    }

    /**
     * Displays an error message when a save file cannot be loaded.
     *
     * @param message The message describing the problem.
     */
    public void displayLoadingError(String message) {
        System.out.println("Something is wrong:" + message);
    }

    /**
     * Displays a message confirming that a task has been added.
     *
     * @param task        The task that was added.
     * @param totalTasks  The total number of tasks after addition.
     */
    public void displayTaskAdded(Task task, int totalTasks) {
        System.out.println("Got it. I've added this farquaad.task: ");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Displays a message confirming that a task has been deleted.
     *
     * @param task        The task that was deleted.
     * @param totalTasks  The total number of tasks remaining.
     */
    public void displayTaskDeleted(Task task, int totalTasks) {
        System.out.println("Noted. I've removed this farquaad.task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Displays a message confirming that a task has been marked as done.
     *
     * @param task The task that was marked as done.
     */
    public void displayTaskMarked(Task task) {
        System.out.println("Nice! I've marked this farquaad.task as done:");
        System.out.println("  " + task);
    }

    /**
     * Displays a message confirming that a task has been unmarked.
     *
     * @param task The task that was unmarked.
     */
    public void displayTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this farquaad.task as not done yet:");
        System.out.println("  " + task);
    }

    /**
     * Displays the full list of tasks.
     *
     * @param tasks The {@code TaskList} containing tasks to display.
     */
    public void displayTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    /**
     * Displays the tasks that match a given search keyword.
     *
     * @param matchingTasks The list of matching tasks.
     */
    public void displayMatchingTasks(TaskList matchingTasks) {
        if (matchingTasks.size() == 0) {
            System.out.println("No matching tasks found!");
            return;
        }

        System.out.println("These are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            System.out.println((i + 1) + ". " + matchingTasks.get(i));
        }
    }
}