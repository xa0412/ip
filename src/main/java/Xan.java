import java.util.Scanner;

public class Xan {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Xan!");
        System.out.println("What can I do for you?");
        Scanner sc = new Scanner(System.in);
        String chat = "";
        while (true) {
            chat = sc.nextLine();
            System.out.println(chat);
            if (chat.equals("bye")) {
                break;
            }
        }
        System.out.println("bye. Hope to see you again!");
    }
}
