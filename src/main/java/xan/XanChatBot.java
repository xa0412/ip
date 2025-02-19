package xan;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import task.TaskManager;
import ui.Ui;

/**
 * Represents the main chatbot program.
 */
public class XanChatBot {
    private static final String DIRECTORY = "data";
    private static final String FILE_PATH = DIRECTORY + "/xan.txt";
    private final TaskManager taskManager;
    private final Ui ui;
    private boolean isExit;

    /**
     * Creates an instance of XanChatBot.
     */
    public XanChatBot() {
        this.ui = new Ui();
        ensureFileExists();
        this.taskManager = new TaskManager(FILE_PATH);
        this.isExit = false;
        taskManager.loadTaskFromFile();
    }
    /**
     * Ensures that the file exists.
     */
    private void ensureFileExists() {
        try {
            // Ensure directory exists
            Files.createDirectories(Paths.get(DIRECTORY));

            // Ensure file exists
            Path filePath = Paths.get(FILE_PATH);
            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
                System.out.println("Created file: " + filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error creating file: " + e.getMessage(), e);
        }
    }
    /**
     * Returns the welcome message.
     *
     * @return The welcome message.
     */
    public String getWelcomeMessage() {
        return ui.getWelcomeMessage();
    }
    /**
     * Returns the status of exit.
     *
     * @return True or False.
     */
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
        assert taskManager != null : "TaskManager should be initialized!";
        input = input.trim();
        try {
            if (input.equals("bye")) {
                isExit = true;
                return ui.getGoodbyeMessage();
            } else if (input.equals("help")) {
                return ui.getHelpMessage();
            } else if (input.equals("list")) {
                return taskManager.showTask();
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
                throw new IllegalArgumentException("please input the correct command, "
                        + "press help to see the list of commands.");
            }
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}
