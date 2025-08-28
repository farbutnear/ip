import java.util.Scanner;

public class farquaad {
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
            System.out.println(input);
        }
    }
}
