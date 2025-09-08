import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void displayWelcome() {
        System.out.println("Hello! I'm Farquaad");
        System.out.println("What can I do for you?");
    }

    public void displayGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void displayError(String message) {
        System.out.println(message);
    }

    public void displayLoadingError(String message) {
        System.out.println("Something is wrong:" + message);
    }

    public void displayTaskAdded(Task task, int totalTasks) {
        System.out.println("Got it. I've added this task: ");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    public void displayTaskDeleted(Task task, int totalTasks) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    public void displayTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    public void displayTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    public void displayTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }
}