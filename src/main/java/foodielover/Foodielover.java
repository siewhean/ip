package foodielover;

import java.io.IOException;

import foodielover.storage.Storage;
import foodielover.task.Deadline;
import foodielover.task.Event;
import foodielover.task.Task;
import foodielover.task.TaskList;
import foodielover.task.Todo;
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
        ui.showGoodbye();
    }

    /**
     * Reads and processes user commands until the exit command is received.
     */
    private static void runCommandLoop() {
        String input = "";

        while (!input.equals("bye") && ui.hasCommand()) {
            input = ui.readCommand();
            ui.showLine();

            if (input.equals("bye")) {
                break;
            }
            try {
                handleCommand(input);
            } catch (FoodieloverException exception) {
                ui.showError(exception.getMessage());
            }
            ui.showLine();
        }
    }

    /**
     * Dispatches user input to the corresponding action handler.
     *
     * @param input Raw command line entered by the user.
     * @throws FoodieloverException If the command keyword is unrecognized or execution fails.
     */
    private static void handleCommand(String input) throws FoodieloverException {
        if (input.equals("list")) {
            listTasks();
        } else if (input.equals("mark") || input.startsWith("mark ")) {
            markTask(input);
        } else if (input.equals("unmark") || input.startsWith("unmark ")) {
            unmarkTask(input);
        } else if (input.equals("todo") || input.startsWith("todo ")) {
            addTodo(input);
        } else if (input.equals("deadline") || input.startsWith("deadline ")) {
            addDeadline(input);
        } else if (input.equals("event") || input.startsWith("event ")) {
            addEvent(input);
        } else if (input.equals("delete") || input.startsWith("delete ")) {
            addDelete(input);
        } else {
            throw new FoodieloverException(
                    "This is not a valid input. Please try again. "
                            + "With the following: add, mark, unmark, todo, deadline, event, list");
        }
    }

    /**
     * Prints all tasks currently stored in the task list.
     */
    private static void listTasks() {
        ui.showTaskList(tasks);
    }

    /**
     * Marks the specified task as completed.
     *
     * @param input Command string containing the 1-based task index.
     * @throws FoodieloverException If the task index is missing, non-numeric, or out of range.
     */
    private static void markTask(String input) throws FoodieloverException {
        int taskIndex = parseTaskIndex(input, "mark");
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new FoodieloverException(
                    "This is not a valid task number. Please enter a number from 1 to " + tasks.size() + ".");
        }
        tasks.get(taskIndex).markAsDone();
        saveTasks();
        ui.showTaskMarked(tasks.get(taskIndex));
    }

    /**
     * Marks the specified task as not completed.
     *
     * @param input Command string containing the 1-based task index.
     * @throws FoodieloverException If the task index is missing, non-numeric, or out of range.
     */
    private static void unmarkTask(String input) throws FoodieloverException {
        int taskIndex = parseTaskIndex(input, "unmark");
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new FoodieloverException(
                    "This is not a valid task number. Please enter a number from 1 to " + tasks.size() + ".");
        }
        tasks.get(taskIndex).markAsUndone();
        saveTasks();
        ui.showTaskUnmarked(tasks.get(taskIndex));
    }

    /**
     * Parses and adds a Todo task.
     *
     * @param input Command string containing the todo description.
     * @throws FoodieloverException If the todo description is empty.
     */
    private static void addTodo(String input) throws FoodieloverException {
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            throw new FoodieloverException("Please enter a description after 'todo'.");
        }
        addTask(new Todo(description));
    }

    /**
     * Parses and adds a Deadline task.
     *
     * @param input Command string containing the deadline description and '/by' parameter.
     * @throws FoodieloverException If '/by' is missing or fields are empty.
     */
    private static void addDeadline(String input) throws FoodieloverException {
        int byIndex = input.indexOf("/by");
        if (byIndex < 0) {
            throw new FoodieloverException("Please include a deadline using '/by'.");
        }
        String description = input.substring(8, byIndex).trim();
        String by = input.substring(byIndex + 3).trim();
        if (description.isEmpty() || by.isEmpty()) {
            throw new FoodieloverException(
                    "Please provide both a deadline description and a value after '/by'.");
        }
        addTask(new Deadline(description, by));
    }

    /**
     * Parses and adds an Event task.
     *
     * @param input Command string containing description, '/from', and '/to' parameters.
     * @throws FoodieloverException If delimiters are missing or fields are empty.
     */
    private static void addEvent(String input) throws FoodieloverException {
        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");
        if (fromIndex < 0 || toIndex < 0 || fromIndex >= toIndex) {
            throw new FoodieloverException(
                    "Please include an event description, '/from' time, and '/to' time.");
        }
        String description = input.substring(5, fromIndex).trim();
        String from = input.substring(fromIndex + 5, toIndex).trim();
        String to = input.substring(toIndex + 3).trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new FoodieloverException(
                    "Please provide an event description and values after '/from' and '/to'.");
        }
        addTask(new Event(description, from, to));
    }

    /**
     * Parses the task index and deletes the specified task.
     *
     * @param input Command string containing the 1-based task index to delete.
     * @throws FoodieloverException If the task index is missing, non-numeric, or out of range.
     */
    private static void addDelete(String input) throws FoodieloverException {
        int deleteIndex = parseTaskIndex(input, "delete");
        if (deleteIndex < 0 || deleteIndex >= tasks.size()) {
            throw new FoodieloverException(
                    "This is not a valid task number. Please enter a number from 1 to " + tasks.size() + ".");
        } else {
            removeTask(deleteIndex);
        }
    }

    /**
     * Stores a typed task and prints confirmation.
     *
     * @param task Task instance to be added.
     */
    private static void addTask(Task task) {
        tasks.add(task);
        saveTasks();
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Removes the task at the specified zero-based index.
     *
     * @param taskIndex Zero-based index of the task to remove.
     * @throws FoodieloverException If the task list is empty.
     */
    private static void removeTask(int taskIndex) throws FoodieloverException {
        if (tasks.isEmpty()) {
            throw new FoodieloverException(
                    "Your task list is empty. Please add a task before removing one.");
        }

        Task removedTask = tasks.remove(taskIndex);
        saveTasks();
        ui.showTaskRemoved(removedTask, tasks.size());
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

    /**
     * Saves the current tasks to the storage file.
     */
    private static void saveTasks() {
        try {
            storage.save(tasks);
        } catch (IOException exception) {
            ui.showSaveError(exception.getMessage());
        }
    }

    /**
     * Parses a 1-based task number from a mark or unmark command.
     *
     * @param input Command containing the task number.
     * @param command Command keyword used in the input.
     * @return Zero-based task index.
     * @throws FoodieloverException If the argument is missing or not a valid number.
     */
    private static int parseTaskIndex(String input, String command) throws FoodieloverException {
        String argument = input.substring(command.length()).trim();
        if (argument.isEmpty()) {
            throw new FoodieloverException("Please enter something after '" + command + "'.");
        }
        try {
            return Integer.parseInt(argument) - 1;
        } catch (NumberFormatException exception) {
            throw new FoodieloverException("Please enter a number after '" + command + "'.");
        }
    }
}