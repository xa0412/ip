package xan;

import xan.ui.Ui;

import java.util.Scanner;

public class XanChatBot {
    private static final String FILE_PATH = "src/main/data/xan.txt";
    private final TaskManager taskManager;
    private final Ui ui;

    /**
     * Creates an instance of XanChatBot.
     */
    public XanChatBot() {
        this.ui = new Ui();
        this.taskManager = new TaskManager(FILE_PATH);
    }

    /**
     * Run the main program.
     */
    public void run() {
        ui.welcomeMessage();
        taskManager.loadTask();
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                String chat = sc.nextLine().trim();
                if (chat.equals("bye")) {
                    ui.showGoodbyeMessage();
                    break;
                } else if (chat.equals("list")) {
                    taskManager.showList();
                } else if (chat.startsWith("mark")) {
                    taskManager.markTask(chat);
                } else if (chat.startsWith("unmark")) {
                    taskManager.unmarkTask(chat);
                } else if (chat.startsWith("delete")) {
                    taskManager.deleteTask(chat);
                } else if (chat.startsWith("search")) {
                    taskManager.searchTask(chat);
                } else {
                    taskManager.addList(chat);
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new XanChatBot().run();
    }
}
