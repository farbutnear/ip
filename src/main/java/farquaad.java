import java.util.ArrayList;
import java.util.Scanner;

public class farquaad {
    static ArrayList<String> list = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Hello! I'm Farquaad");
        System.out.println("What can I do for you?");

        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();
            if (input.equals("list")) {
                for (int i = 0; i < list.size(); i++) {
                    System.out.println((i + 1) + ". " + list.get(i));
                }
            } else if (input.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else {
                list.add(input);
                System.out.println("added: " + input);
            }
            System.out.println(input);
        }
    }
}
