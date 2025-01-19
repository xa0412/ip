import java.util.ArrayList;
import java.util.Scanner;

public class Xan {
    private static final ArrayList<Task> listArray =  new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Hello! I'm Xan! I am your personalized Task manager!");
        System.out.println("What can I do for you?");
        Scanner sc = new Scanner(System.in);
        String chat = "";

        while (true) {
            chat = sc.nextLine();
            if (chat.equals("bye")) {
                System.out.println("bye. Hope to see you again!");
                break;
            } else if (chat.equals("list")) {
                showList();
            } else if (chat.startsWith("mark")) {
                markTask(chat);
            } else if (chat.startsWith("unmark")) {
                unmarkTask(chat);
            } else {
                addList(chat);
            }
        }
    }

    private static void showList() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < listArray.size(); i++) {
            Task task = listArray.get(i);
            System.out.println((i + 1) + "." + task.toString());
        }
    }

    private static void addList(String chat) {
        Task task = new Task(chat);
        listArray.add(task);
        System.out.println("added: " + chat);
    }

    private static void markTask(String chat) {
        int taskIndex = Integer.parseInt(chat.split(" ")[1]) - 1;
        Task task = listArray.get(taskIndex);
        task.markAsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println((taskIndex + 1) + "." + task.toString());
    }

    private static void unmarkTask(String chat) {
        int taskIndex = Integer.parseInt(chat.split(" ")[1]) - 1;
        Task task = listArray.get(taskIndex);
        task.markAsNotDone();
        System.out.println("Ok, I've marked this task as not done:");
        System.out.println((taskIndex + 1) + "." + task.toString());
    }
}
