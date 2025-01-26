package Xan;

import Xan.exception.XanException;
import Xan.ui.Ui;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

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

    public void markTask(String chat) {
        int taskIndex = Integer.parseInt(chat.split(" ")[1]) - 1;
        Task task = listArray.get(taskIndex);
        task.markAsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println((taskIndex + 1) + "." + task.toString());
        saveTask();
    }

    public void unmarkTask(String chat) {
        int taskIndex = Integer.parseInt(chat.split(" ")[1]) - 1;
        Task task = listArray.get(taskIndex);
        task.markAsNotDone();
        System.out.println("Ok, I've marked this task as not done:");
        System.out.println((taskIndex + 1) + "." + task.toString());
        saveTask();
    }

    public void addTaskMessage(Task task) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + listArray.size() + " tasks in the list.");
    }

    public void deleteTask(String chat) {
        int taskIndex = Integer.parseInt(chat.split(" ")[1]) - 1;
        Task task = listArray.get(taskIndex);
        System.out.println("Noted. I've remove this task:");
        System.out.println((taskIndex + 1) + "." + task.toString());
        listArray.remove(taskIndex);
        System.out.println("Now you have " + listArray.size() + " tasks in the list.");
        saveTask();
    }

    public int getListArraySize() {
        return listArray.size();
    }

    public Task getTask(int index) {
        if (index < 0 || index >= listArray.size()) {
            throw new IndexOutOfBoundsException("Invalid task index: " + index);
        }
        return listArray.get(index);
    }
}
