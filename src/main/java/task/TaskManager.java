package xan;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import exception.XanException;
import ui.Ui;

/**
 * The TaskManager class manages a list of tasks, including creating, editing,
 * marking, unmarking, deleting, saving, and loading tasks. It also provides functionality
 * to display tasks to the user and supports different types of tasks such as Todo, Deadline, and Event.
 */
public class TaskManager {
    private static final ArrayList<Task> listArray = new ArrayList<>();
    private final String filePath;
    private Ui ui;

    public TaskManager(String filePath) {
        this.filePath = filePath;
    }

    public TaskManager() {
        this.filePath = null;
    }

    /**
     * Categorizes tasks as TODO, DEADLINE, or EVENT and provides utility for
     * converting string representations.
     */
    private enum TaskType {
        TODO, DEADLINE, EVENT;

        public static TaskType fromString(String input) {
            return switch (input.toLowerCase()) {
                case "todo" -> TODO;
                case "deadline" -> DEADLINE;
                case "event" -> EVENT;
                default -> throw new IllegalArgumentException("Unknown task type: " + input);
            };
        }
    }

    /**
     * Loads tasks from a file or creates a test Todo task if no file path is provided.
     * Handles parsing for Todo, Deadline, and Event tasks and appropriate exceptions.
     * @throws FileNotFoundException - If the specified file path does not exist.
     * @throws IllegalArgumentException - If an unknown task type or invalid task format is encountered.
     */
    public void loadTask() {
        if (filePath == null) {
            // Skip loading from file for tests; Use some hardcoded data
            // For testing purposes, add a sample task manually:
            listArray.add(new Todo("todo Read a book"));
            return;
        }

        try {
            File inputFile = new File(filePath);
            if (!inputFile.exists()) {
                throw new FileNotFoundException(filePath);
            }

            Scanner sc = new Scanner(inputFile);
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                char taskTypeChar = line.charAt(1);
                boolean isDone = line.charAt(4) == 'X';

                String taskDetails = line.substring(7);
                Task task = null;

                switch (taskTypeChar) {
                case 'T':
                    task = new Todo(taskDetails);
                    break;
                case 'D':
                    if (taskDetails.contains("(by: ")) {
                        String[] parts = taskDetails.split("\\(by: ");
                        String description = parts[0].trim();
                        LocalDate date = LocalDate.parse(parts[1].replace(")", "").trim());
                        task = new Deadline(description, date);
                    }
                    break;
                case 'E':
                    if (taskDetails.contains("(from: ") && taskDetails.contains("to: ")) {
                        String[] parts = taskDetails.split("(\\(from: )| (to: )");
                        String description = parts[0].trim();
                        String startTime = parts[1].trim();
                        String endTime = parts[2].replace(")", "").trim();
                        task = new Event(description, startTime, endTime);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown task type: " + taskTypeChar);
                }

                if (task != null) {
                    if (isDone) {
                        task.markAsDone();
                    }
                    listArray.add(task);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file could not be found: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error in parsing task: " + e.getMessage());
        }
    }

    /**
     * Saves the current list of tasks to a file. Handles exceptions for file not found
     * or write errors.
     * Exceptions handled:
     * @throws IOException: If an error occurs while writing to the file, a RuntimeException is thrown
     *   with an appropriate error message.
     * @throws IllegalArgumentException: If the file does not exist, an exception is thrown with a message
     *   indicating the invalid file path.
     */
    public void saveTask() {
        if (filePath == null) {
            return;
        }

        try {
            File inputFile = new File(filePath);
            if (!inputFile.exists()) {
                throw new IllegalArgumentException("The file could not be found: " + filePath);
            }

            FileWriter fw = new FileWriter(inputFile);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Task task : listArray) {
                bw.write(task.toString());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Displays all tasks in the list, or a message if the list is empty.
     */
    public String showList() {
        if (listArray.isEmpty()) {
            return "No tasks found";
        } else {
            StringBuilder result = new StringBuilder();
            result.append("Here are the tasks in your list:\n");
            for (int i = 0; i < listArray.size(); i++) {
                Task task = listArray.get(i);
                result.append((i + 1)).append(". ").append(task.toString()).append("\n");
            }
            return result.toString();
        }
    }

    /**
     * Adds a task based on the input. Supports "todo", "deadline", and "event" tasks.
     * Validates inputs and handles exceptions for invalid formats.
     *
     * @param chat Command string containing task type and details.
     *
     * @throws IllegalArgumentException If the task type is invalid or if the input string does not
     *                                  match the required format for the specified task type.
     */
    public String addList(String chat) throws IllegalArgumentException {
        String[] words = chat.split(" ", 2);
        if (words[0].equals("todo") || words[0].equals("deadline") || words[0].equals("event")) {
            if (words.length < 2) {
                throw new XanException("The description of a task cannot be empty!");
            }

            TaskType taskType = TaskType.fromString(words[0]);
            String details = words[1];
            Task task = null;

            switch (taskType) {
            case TODO:
                task = new Todo(details);
                break;

            case DEADLINE:
                if (!details.contains("/by ")) {
                    throw new XanException("A deadline task must have a '/by' clause!");
                }
                String[] deadlineParts = details.split("/by ", 2);
                LocalDate date = LocalDate.parse(deadlineParts[1].replace(")", "").trim());
                task = new Deadline(deadlineParts[0].trim(), date);
                break;

            case EVENT:
                if (!details.contains("/from ") || !details.contains("/to ")) {
                    throw new XanException("An event task must have both '/from' and '/to' clauses!");
                }
                String[] eventParts = details.split("/from | /to ");
                task = new Event(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim());
                break;
            }
            listArray.add(task);
            saveTask();
            return addTaskMessage(task);
        } else {
            throw new IllegalArgumentException("Invalid task type! Please use 'todo', 'deadline',"
                    + " 'event', 'delete'," + " 'list', 'mark', 'unmark'.");
        }
    }

    /**
     * Marks a task as done based on its index from the input.
     *
     * @param chat Command string specifying the task index (e.g., "mark 1").
     */
    public String markTask(String chat) {
        int taskIndex = Integer.parseInt(chat.split(" ")[1]) - 1;
        Task task = listArray.get(taskIndex);
        task.markAsDone();

        String message = "Nice! I've marked this task as done:\n" + (
                taskIndex + 1) + "." + task.toString() + "\n";
        saveTask();
        return message;
    }

    /**
     * Unmarks a task as not done based on its index from the input.
     *
     * @param chat Command string specifying the task index (e.g., "unmark 1").
     */
    public String unmarkTask(String chat) {
        int taskIndex = Integer.parseInt(chat.split(" ")[1]) - 1;
        Task task = listArray.get(taskIndex);
        task.markAsNotDone();

        String message = "Ok, I've marked this task as not done:\n" + (
                taskIndex + 1) + "." + task.toString() + "\n";
        saveTask();
        return message;
    }

    /**
     * Prints confirmation after adding a task, showing the total task count.
     *
     * @param task Task object being added.
     */
    public String addTaskMessage(Task task) {
        return "Got it. I've added this task:\n"
                + task.toString() + "\n"
                + "Now you have " + listArray.size() + " tasks in the list.";
    }

    /**
     * Deletes a task based on its index from the input and confirms the deletion.
     *
     * @param chat Command string specifying the task index (e.g., "delete 1").
     */
    public String deleteTask(String chat) {
        int taskIndex = Integer.parseInt(chat.split(" ")[1]) - 1;
        Task task = listArray.get(taskIndex);

        StringBuilder message = new StringBuilder();
        message.append("Noted. I've removed this task:\n");
        message.append(taskIndex + 1).append(".").append(task.toString()).append("\n");

        listArray.remove(taskIndex);
        message.append("Now you have ").append(listArray.size()).append(" tasks in the list.\n");
        saveTask();
        return message.toString();
    }

    /**
     * Returns the number of tasks in the list.
     */
    public int getListArraySize() {
        return listArray.size();
    }

    /**
     * Retrieves a task by index.
     *
     * @param index Index of the task to retrieve.
     * @return Task object at the specified index.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    public Task getTask(int index) {
        if (index < 0 || index >= listArray.size()) {
            throw new IndexOutOfBoundsException("Invalid task index: " + index);
        }
        return listArray.get(index);
    }

    /**
     * Searches for tasks in the list that contain the given keyword in their description.
     * If no keyword is provided or the keyword is empty, prompts the user to provide a valid keyword.
     * Displays all matching tasks or a message if no tasks are found.
     *
     * @param chat The command string containing the search keyword (e.g., "search keyword").
     */
    public String searchTask(String chat) {
        String[] splitWord = chat.split(" ", 2);
        if (splitWord.length < 2 || splitWord[1].trim().isEmpty()) {
            return "Please provide a keyword to search.";
        }
        String keyWord = splitWord[1].trim();

        StringBuilder message = new StringBuilder();
        message.append("Here are the matching tasks in your list:\n");

        boolean found = false;
        int displaySearchIndex = 1;
        for (Task task : listArray) {
            if (task.getDescription().toLowerCase().contains(keyWord.toLowerCase())) {
                message.append(displaySearchIndex).append(".").append(task).append("\n");
                displaySearchIndex++;
                found = true;
            }
        }
        if (!found) {
            message.append("No matching tasks found.\n");
        }
        return message.toString();
    }
}
