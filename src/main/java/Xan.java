import java.util.ArrayList;
import java.util.Scanner;

public class Xan {
    private static final ArrayList<Task> listArray =  new ArrayList<>();

    public static void main(String[] args) {
        welcomeMessage();
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                String chat = sc.nextLine().trim();
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
            } catch (XanException e) {
                System.out.println(e.getMessage());
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

    private static void addList(String chat) throws XanException {
        if (chat.startsWith("todo")) {
            if (chat.length() < 5) {
                throw new XanException("Description of todo cannot be empty!");
            }
            String description = chat.substring(5).trim();
            Task task = new Todo(description);
            listArray.add(task);
            addTask(task);
        } else if (chat.startsWith("deadline")) {
            if (chat.length() < 9) {
                throw new XanException("Description/Due date of deadline cannot be empty!");
            }
            String[] split = chat.split("/by ");
            String description = split[0].substring(9);
            String by = split[1];
            Task task = new Deadline(description, by);
            listArray.add(task);
            addTask(task);
        } else if (chat.startsWith("event")) {
            if (chat.length() < 6) {
                throw new XanException("Description/Start time/End time of event cannot be empty!");
            }
            String[] split = chat.split("/from | /to ");
            String description = split[0].substring(6);
            String start = split[1];
            String end = split[2];
            Task task = new Event(description, start, end);
            listArray.add(task);
            addTask(task);
        } else {
            throw new XanException("OOPS! Unknown command!");
        }
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

    private static void addTask(Task task) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + listArray.size() + " tasks in the list.");
    }

    private static void welcomeMessage() {
        System.out.println("Hello! I'm Xan! I am your personalized Task manager!");
        System.out.println("Enter the following commands to get started:");
        System.out.println("  todo: tasks without any date/time attached to it. e.g. todo visit new theme park");
        System.out.println("  deadline: tasks that need to be done before a specific date/time. " +
                "e.g. deadline submit report by 11/10/2019 5pm");
        System.out.println("  event: tasks that start at a specific date/time and ends at a specific date/time. " +
                "e.g. event project meeting /from Mon 2pm /to 4pm");
        System.out.println("  list: to show all the tasks in your list.");
        System.out.println("  mark: to mark list as marked as completed. e.g. mark 1");
        System.out.println("  unmark: to unmark list as not completed. e.g. unmark 1");
        System.out.println("What can I do for you?");
    }
}
