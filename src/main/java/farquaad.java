import java.util.ArrayList;
import java.util.Scanner;

public class farquaad {
    static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Hello! I'm Farquaad");
        System.out.println("What can I do for you?");

        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();
            if (input.equals("list")) {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
            } else if (input.startsWith("mark")) {
                int taskNo = Integer.parseInt(input.split(" ")[1]) - 1;

                tasks.get(taskNo).markAsDone();
                System.out.println(tasks.get(taskNo));
            } else if (input.startsWith("unmark")) {
                int taskNo = Integer.parseInt(input.split(" ")[1]) - 1;

                tasks.get(taskNo).unmarkAsNotDone();
                System.out.println(tasks.get(taskNo));
            } else if (input.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else {
                Task task = new Task(input);
                tasks.add(task);
                System.out.println("added: " + input);
            }
        }
    }
}
