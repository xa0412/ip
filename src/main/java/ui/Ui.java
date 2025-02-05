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
    public String getWelcomeMessage() {
        return """
                Hello! I'm Xan! I am your personalized task manager!
                Enter the following commands to get started:
                  - todo: tasks without any date/time attached to it. e.g. todo visit new theme park
                  - deadline: tasks that need to be done before a specific date/time. \
                e.g. deadline return book /by 2019-12-01
                  - event: tasks that start at a specific date/time and end at a specific date/time. \
                e.g. event project meeting /from Mon 2pm /to 4pm
                  - delete: to delete a task. e.g. delete 1
                  - list: to show all the tasks in your list.
                  - mark: to mark a task as completed. e.g. mark 1
                  - unmark: to unmark a task as not completed. e.g. unmark 1
                What can I do for you?
                """;
    }

    /**
     * Displays a goodbye message to the user.
     */
    public String getGoodbyeMessage() {
        return "Goodbye! Have a nice day!";
    }
}
