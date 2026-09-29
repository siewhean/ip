package foodielover;

import foodielover.command.Command;
import foodielover.parser.Parser;
import foodielover.storage.Storage;
import foodielover.task.TaskList;
import foodielover.ui.Ui;

/**
 * Entry point for the Foodielover chatbot application.
 */
public class Foodielover {
    /** File path for persisting task data. */
    private static final String FILE_PATH = "./data/foodielover.txt";

    /** User interface handler for input and output interactions. */
    private static final Ui ui = new Ui();

    /** Storage handler for persisting and loading task data. */
    private static final Storage storage = new Storage(FILE_PATH);

    /** In-memory task list. */
    private static TaskList tasks = new TaskList();

    /**
     * Runs the Foodielover application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        loadTasks();
        ui.showGreeting();
        runCommandLoop();
    }

    /**
     * Reads and processes user commands until the exit command is received.
     */
    private static void runCommandLoop() {
        boolean isExit = false;

        while (!isExit && ui.hasCommand()) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (FoodieloverException exception) {
                ui.showError(exception.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Loads tasks from the storage file into the task list.
     */
    private static void loadTasks() {
        try {
            tasks = new TaskList(storage.load());
        } catch (FoodieloverException exception) {
            ui.showError(exception.getMessage());
            tasks = new TaskList();
        }
    }
}