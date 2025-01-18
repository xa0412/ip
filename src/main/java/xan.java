import java.util.Scanner;

public class xan {
    public static void main(String[] args) {
        System.out.println("Hello! I'm xan!");
        System.out.println("What can I do for you?");

        Scanner sc = new Scanner(System.in);
        String chat = "";
        while (!chat.equals("bye")) {
            chat = sc.nextLine();
            if (!chat.equals("bye")) {
                System.out.println(chat);
            }
        }
        System.out.println("bye. Hope to see you again!");
    }
}
