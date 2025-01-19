import java.util.ArrayList;
import java.util.Scanner;

public class Xan {
    private static final ArrayList<String> listArray =  new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Hello! I'm Xan!");
        System.out.println("What can I do for you?");
        Scanner sc = new Scanner(System.in);
        String chat = "";
        while (true) {
            chat = sc.nextLine();
            if (chat.equals("bye")) {
                System.out.println("bye. Hope to see you again!");
                break;
            }
            else if (chat.equals("list")) {
                showList();
            } else {
                addList(chat);
            }
        }
    }

    private static void showList() {
        for (int i = 0; i < listArray.size(); i++) {
            System.out.println((i + 1) + ". " + listArray.get(i));
        }
    }

    private static void addList(String chat) {
        listArray.add(chat);
        System.out.println("added: " + chat);
    }
}
