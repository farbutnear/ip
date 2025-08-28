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
                System.out.println("Got it. I've added this task: ");

                if (input.startsWith("todo")) {
                    Task toDo = new Task.ToDo(input.substring(5));

                    tasks.add(toDo);
                    System.out.println(" " + toDo.toString());
                } else if (input.startsWith("deadline")) {
                    String[] splits = input.substring(9).split(" /by ");
                    Task deadline = new Task.Deadline(splits[0], splits[1]);

                    tasks.add(deadline);
                    System.out.println("  " + deadline.toString());
                } else if (input.startsWith("event")) {
                    String[] firstSplit = input.substring(6).split(" /from ");
                    String[] secondSplit = firstSplit[1].split("/to ");
                    Task event = new Task.Event(firstSplit[0], secondSplit[0], secondSplit[1]);

                    tasks.add(event);
                    System.out.println("  " + event.toString());
                }

                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            }
        }
    }
}
