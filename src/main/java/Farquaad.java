import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
class FarquaadException extends Exception {
    FarquaadException(String msg) { super(msg); }
}

class UnknownCommandException extends FarquaadException {
    UnknownCommandException() {
        super("I apologise, but I don't know what that means LOL");
    }
}

class EmptyDescriptionException extends FarquaadException {
    EmptyDescriptionException(String task) {
        super("lmao the description of a " + task + " cannot be empty.");
    }
}

class InvalidIndexException extends FarquaadException {
    InvalidIndexException() {
        super("the task number is invalid.");
    }
}

class InvalidDateFormatException extends FarquaadException {
    InvalidDateFormatException(String command) {
        super("oi provide a valid date format for " + command + ". " +
                "Examples: 2024-12-25, 25/12/2024, Dec 25 2024");
    }
}

class MissingDateTimeException extends FarquaadException {
    MissingDateTimeException(String command) {
        super("can you provide the required date/time information for " + command + "...");
    }
}

public class Farquaad {
    static ArrayList<Task> tasks = new ArrayList<>();
    static final String FILE_NAME = "data/farquaad.txt";
    static final SaveFunction savefunction = new SaveFunction(FILE_NAME);

    public static void main(String[] args) {
        System.out.println("Hello! I'm Farquaad");
        System.out.println("What can I do for you?");

        try {
            tasks = savefunction.load();
        } catch (IOException e) {
            System.out.println("Something is wrong:" + e.getMessage());
        }

        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }
            try {
                handle(input);
            } catch (FarquaadException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("Cannot save file! " + e.getMessage());
            }
        }
    }

    static void handle(String input) throws FarquaadException, IOException {
        if (input.isEmpty()) throw new UnknownCommandException();

        if (input.equals("list")) {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        } else if (input.startsWith("mark")) {
            int taskNo = Integer.parseInt(input.split(" ")[1]) - 1;

            tasks.get(taskNo).markAsDone();
            System.out.println("Ok, solid bird bird, marked this as done:");
            System.out.println(tasks.get(taskNo));
            savefunction.save(tasks);
        } else if (input.startsWith("unmark")) {
            int taskNo = Integer.parseInt(input.split(" ")[1]) - 1;

            tasks.get(taskNo).unmarkAsNotDone();
            System.out.println(tasks.get(taskNo));
            savefunction.save(tasks);
        } else {
            if (input.startsWith("todo")) {
                String work = (input.length() > 4) ? input.substring(4).trim() : "";

                if (work.isEmpty()) throw new EmptyDescriptionException("todo");
                Task toDo = new Task.ToDo(work);

                tasks.add(toDo);
                System.out.println("Got it. I've added this task: ");
                System.out.println(" " + toDo);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                savefunction.save(tasks);
            } else if (input.startsWith("deadline")) {
                String work = (input.length() > 8) ? input.substring(8).trim() : "";

                if (work.isEmpty()) throw new EmptyDescriptionException("deadline");

                if (!work.contains(" /by ")) {
                    throw new MissingDateTimeException("use: deadline <description> /by <date>");
                }

                String[] splits = work.split(" /by ", 2);
                if (splits.length != 2 || splits[0].trim().isEmpty() || splits[1].trim().isEmpty()) {
                    throw new MissingDateTimeException("deadline");
                }

                Task deadline = new Task.Deadline(splits[0].trim(), splits[1].trim());

                tasks.add(deadline);
                System.out.println("Got it. I've added this task: ");
                System.out.println("  " + deadline.toString());
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                savefunction.save(tasks);
            } else if (input.startsWith("event")) {
                String work = (input.length() > 5) ? input.substring(5).trim() : "";

                if (work.isEmpty()) throw new EmptyDescriptionException("event");

                if (!work.contains(" /from ") || !work.contains(" /to ")) {
                    throw new MissingDateTimeException("use: event <description> /from <start> /to <end>");
                }

                String[] firstSplit = work.split(" /from ", 2);
                if (firstSplit.length != 2 || firstSplit[0].trim().isEmpty()) {
                    throw new MissingDateTimeException("event");
                }

                String[] secondSplit = firstSplit[1].split(" /to ");
                if (secondSplit.length != 2 || secondSplit[0].trim().isEmpty() || secondSplit[1].trim().isEmpty()) {
                    throw new MissingDateTimeException("event");
                }

                Task event = new Task.Event(firstSplit[0].trim(), secondSplit[0].trim(), secondSplit[1].trim());

                tasks.add(event);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + event.toString());
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                savefunction.save(tasks);
            } else if (input.startsWith("delete")) {
                String num = (input.length() > 6) ? input.substring(6).trim() : "";
                if (num.isEmpty()) throw new EmptyDescriptionException("delete");

                int taskNo;
                try {
                    taskNo = Integer.parseInt(num) - 1;   // 1-based -> 0-based
                } catch (NumberFormatException e) {
                    throw new InvalidIndexException();
                }

                if (taskNo < 0 || taskNo >= tasks.size()) throw new InvalidIndexException();

                Task removed = tasks.remove(taskNo);
                System.out.println("Noted. I've removed this task:");
                System.out.println("  " + removed);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                savefunction.save(tasks);
            } else {
                throw new UnknownCommandException();
            }
        }
    }
}
