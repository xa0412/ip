package xan;

import xan.exception.XanException;
import xan.ui.Ui;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

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
    public void showList() {
        if (listArray.isEmpty()) {
            System.out.println("No tasks found");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < listArray.size(); i++) {
                Task task = listArray.get(i);
                System.out.println((i + 1) + "." + task.toString());
            }
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
    public void addList(String chat) throws IllegalArgumentException {
        String[] words = chat.split(" ", 2);
        if (words[0].equals("todo") || words[0].equals("deadline") || words[0].equals("event")) {
            if (words.length < 2) {
                throw new XanException("The description of a task cannot be empty!");
            }

            TaskType taskType = TaskType.fromString(words[0]);
            String details = words[1];

            switch (taskType) {
            case TODO:
                Task todoTask = new Todo(details);
                listArray.add(todoTask);
                addTaskMessage(todoTask);
                break;
            case DEADLINE:
                if (!details.contains("/by ")) {
                    throw new XanException("A deadline task must have a '/by' clause!");
                }
                String[] deadlineParts = details.split("/by ", 2);
                LocalDate date = LocalDate.parse(deadlineParts[1].replace(")", "").trim());
                Task deadlineTask = new Deadline(deadlineParts[0].trim(), date);
                listArray.add(deadlineTask);
                addTaskMessage(deadlineTask);
                break;
            case EVENT:
                if (!details.contains("/from ") || !details.contains("/to ")) {
                    throw new XanException("An event task must have both '/from' and '/to' clauses!");
                }
                String[] eventParts = details.split("/from | /to ");
                Task eventTask = new Event(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim());
                listArray.add(eventTask);
                addTaskMessage(eventTask);
                break;
            }
            saveTask();
        } else {
            throw new IllegalArgumentException("Invalid task type! Please use 'todo', 'deadline'," +
                    " 'event', 'delete'," + " 'list', 'mark', 'unmark'.");
        }
    }

    /**
     * Marks a task as done based on its index from the input.
     *
     * @param chat Command string specifying the task index (e.g., "mark 1").
     */
    public void markTask(String chat) {
        int taskIndex = Integer.parseInt(chat.split(" ")[1]) - 1;
        Task task = listArray.get(taskIndex);
        task.markAsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println((taskIndex + 1) + "." + task.toString());
        saveTask();
    }

    /**
     * Unmarks a task as not done based on its index from the input.
     *
     * @param chat Command string specifying the task index (e.g., "unmark 1").
     */
    public void unmarkTask(String chat) {
        int taskIndex = Integer.parseInt(chat.split(" ")[1]) - 1;
        Task task = listArray.get(taskIndex);
        task.markAsNotDone();
        System.out.println("Ok, I've marked this task as not done:");
        System.out.println((taskIndex + 1) + "." + task.toString());
        saveTask();
    }

    /**
     * Prints confirmation after adding a task, showing the total task count.
     *
     * @param task Task object being added.
     */
    public void addTaskMessage(Task task) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + listArray.size() + " tasks in the list.");
    }

    /**
     * Deletes a task based on its index from the input and confirms the deletion.
     *
     * @param chat Command string specifying the task index (e.g., "delete 1").
     */
    public void deleteTask(String chat) {
        int taskIndex = Integer.parseInt(chat.split(" ")[1]) - 1;
        Task task = listArray.get(taskIndex);
        System.out.println("Noted. I've remove this task:");
        System.out.println((taskIndex + 1) + "." + task.toString());
        listArray.remove(taskIndex);
        System.out.println("Now you have " + listArray.size() + " tasks in the list.");
        saveTask();
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
}
