package xan;

import task.TaskManager;
import ui.Ui;

/**
 * Represents the main chatbot program.
 */
public class XanChatBot {
    private static final String FILE_PATH = "src/main/resources/data/xan.txt";
    private final TaskManager taskManager;
    private final Ui ui;
    private boolean isExit;

    /**
     * Creates an instance of XanChatBot.
     */
    public XanChatBot() {
        this.ui = new Ui();
        this.taskManager = new TaskManager(FILE_PATH);
        this.isExit = false;
        taskManager.loadTask();
    }
    /**
     * Returns the welcome message.
     *
     * @return The welcome message.
     */
    public String getWelcomeMessage() {
        return ui.getWelcomeMessage();
    }

    public boolean isExit() {
        return isExit;
    }

    /**
     * Returns the response to the user input.
     *
     * @param input The user input.
     * @return The response to the user input.
     */
    public String getResponse(String input) {
        input = input.trim();
        try {
            if (input.equals("bye")) {
                isExit = true;
                return ui.getGoodbyeMessage();
            } else if (input.equals("list")) {
                return taskManager.showList();
            } else if (input.startsWith("mark")) {
                return taskManager.markTask(input);
            } else if (input.startsWith("unmark")) {
                return taskManager.unmarkTask(input);
            } else if (input.startsWith("delete")) {
                return taskManager.deleteTask(input);
            } else if (input.startsWith("search")) {
                return taskManager.searchTask(input);
            } else if (input.startsWith("todo") || input.startsWith("deadline") || input.startsWith("event")) {
                return taskManager.addTask(input);
            } else {
                throw new IllegalArgumentException("I'm sorry, but I don't know what that means, please try again.");
            }
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}
