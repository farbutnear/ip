import java.util.ArrayList;
import java.util.Scanner;

class farquaadException extends Exception {
    farquaadException(String msg) { super(msg); }
}

class unknownCommandException extends farquaadException {
    unknownCommandException() {
        super("I apologise, but I don't know what that means LOL");
    }
}

class emptyDescriptionException extends farquaadException {
    emptyDescriptionException(String task) {
        super("lmao the description of a " + task + " cannot be empty.");
    }
}

class invalidIndexException extends farquaadException {
    invalidIndexException() {
        super("the task number is invalid.");
    }
}

public class farquaad {
    static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Hello! I'm Farquaad");
        System.out.println("What can I do for you?");

        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }
            try {
                handle(input);
            } catch (farquaadException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static void handle(String input) throws farquaadException {
        if (input.isEmpty()) throw new unknownCommandException();

        if (input.equals("list")) {
            System.out.println("Here are the tasks in your list:");
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
        } else {
            if (input.startsWith("todo")) {
                String work = (input.length() > 4) ? input.substring(4).trim() : "";

                if (work.isEmpty()) throw new emptyDescriptionException("todo");
                Task toDo = new Task.ToDo(work);

                tasks.add(toDo);
                System.out.println("Got it. I've added this task: ");
                System.out.println(" " + toDo);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            } else if (input.startsWith("deadline")) {
                String work = (input.length() > 8) ? input.substring(8).trim() : "";

                if (work.isEmpty()) throw new emptyDescriptionException("deadline");
                String[] splits = work.split(" /by ");
                Task deadline = new Task.Deadline(splits[0], splits[1]);

                tasks.add(deadline);
                System.out.println("Got it. I've added this task: ");
                System.out.println("  " + deadline.toString());
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            } else if (input.startsWith("event")) {
                String work = (input.length() > 5) ? input.substring(5).trim() : "";

                if (work.isEmpty()) throw new emptyDescriptionException("event");
                String[] firstSplit = work.split(" /from ");
                String[] secondSplit = firstSplit[1].split(" /to ");
                Task event = new Task.Event(firstSplit[0], secondSplit[0], secondSplit[1]);

                tasks.add(event);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + event.toString());
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            } else if (input.startsWith("delete")) {
                String num = (input.length() > 6) ? input.substring(6).trim() : "";
                if (num.isEmpty()) throw new emptyDescriptionException("delete");

                int taskNo;
                try {
                    taskNo = Integer.parseInt(num) - 1;   // 1-based -> 0-based
                } catch (NumberFormatException e) {
                    throw new invalidIndexException();
                }

                if (taskNo < 0 || taskNo >= tasks.size()) throw new invalidIndexException();

                Task removed = tasks.remove(taskNo);
                System.out.println("Noted. I've removed this task:");
                System.out.println("  " + removed);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            } else {
                throw new unknownCommandException();
            }
        }
    }
}
