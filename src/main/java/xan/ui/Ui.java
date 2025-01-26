package xan.ui;

/**
 * The Ui class is responsible for interacting with the user, It handles welcome and
 * goodbye messages to the user.
 */
public class Ui {

    public Ui() {
    }

    /**
     * Displays a welcome message to the user, introducing the chatbot and
     * providing instructions on how to use its various features.
     */
    public void welcomeMessage() {
        System.out.println("Hello! I'm Xan! I am your personalized task manager!");
        System.out.println("Enter the following commands to get started:");
        System.out.println("  todo: tasks without any date/time attached to it. e.g. todo visit new theme park");
        System.out.println("  deadline: tasks that need to be done before a specific date/time. "
                + "e.g. deadline return book /by 2019-12-01");
        System.out.println("  event: tasks that start at a specific date/time and ends at a specific date/time. "
                + "e.g. event project meeting /from Mon 2pm /to 4pm");
        System.out.println("  delete: to delete task. e.g. deleted 1");
        System.out.println("  list: to show all the tasks in your list.");
        System.out.println("  mark: to mark list as marked as completed. e.g. mark 1");
        System.out.println("  unmark: to unmark list as not completed. e.g. unmark 1");
        System.out.println("What can I do for you?");
    }

    /**
     * Displays a goodbye message to the user.
     */
    public void showGoodbyeMessage() {
        System.out.println("Goodbye! Have a nice day!");
    }
}
