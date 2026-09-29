package foodielover.ui;

import java.util.List;
import java.util.Scanner;

import foodielover.task.Task;
import foodielover.task.TaskList;

/**
 * Handles interactions with the user such as displaying messages and reading inputs.
 */
public class Ui {
    /** Divider line used to format output messages. */
    private static final String DIVIDER_LINE = "____________________________________________________________";

    /** Banner graphic displayed upon startup. */
    private static final String BANNER = " ______              _ _      _                            \n"
            + "|  ____|            | (_)    | |                           \n"
            + "| |__ ___   ___   __| |_  ___| | _____   _____ _ __        \n"
            + "|  __/ _ \\ / _ \\ / _` | |/ _ \\ |/ _ \\ \\ / / _ \\ '__|       \n"
            + "| | | (_) | (_) | (_| | |  __/ | (_) \\ V /  __/ |          \n"
            + "|_|  \\___/ \\___/ \\__,_|_|\\___|_|\\___/ \\_/ \\___|_|          \n";

    /** Scanner for reading input from the console. */
    private final Scanner scanner;

    /**
     * Constructs a new Ui instance configured for standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads and returns the next line of command input from the user.
     *
     * @return Raw command string entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Checks if there is another line of input available from the user.
     *
     * @return True if another line is available, otherwise false.
     */
    public boolean hasCommand() {
        return scanner.hasNextLine();
    }

    /**
     * Prints the horizontal divider line.
     */
    public void showLine() {
        System.out.println(DIVIDER_LINE);
    }

    /**
     * Prints the startup banner and welcome greeting.
     */
    public void showGreeting() {
        showLine();
        System.out.println(BANNER);
        System.out.println("Hello! I'm Foodielover.");
        System.out.println("What can I do for you?");
        showLine();
    }

    /**
     * Displays the welcome greeting and banner to the user.
     */
    public void showWelcome() {
        showGreeting();
    }

    /**
     * Prints the exit farewell message.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Prints an error message.
     *
     * @param message Error message to display.
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Prints a warning message for corrupted task entries in the storage file.
     *
     * @param corruptedLine Corrupted line text from the storage file.
     */
    public void showCorruptedLineWarning(String corruptedLine) {
        System.out.println("Warning: Skipping corrupted task entry in data file: " + corruptedLine);
    }

    /**
     * Prints an error message when saving tasks fails.
     *
     * @param message Details of the save error.
     */
    public void showSaveError(String message) {
        System.out.println("An error occurred while saving tasks: " + message);
    }

    /**
     * Prints all tasks currently stored in the task list.
     *
     * @param tasks List of tasks to display.
     */
    public void showTaskList(List<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Prints all tasks currently stored in the task list.
     *
     * @param tasks TaskList instance containing tasks to display.
     */
    public void showTaskList(TaskList tasks) {
        showTaskList(tasks.getAll());
    }

    /**
     * Prints confirmation that a task has been added.
     *
     * @param task Task instance that was added.
     * @param totalTasks Total number of tasks in the list after addition.
     */
    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Prints confirmation that a task has been removed.
     *
     * @param task Task instance that was removed.
     * @param totalTasks Total number of tasks in the list after removal.
     */
    public void showTaskRemoved(Task task, int totalTasks) {
        System.out.println("Noted. I've removed this task:\n" + task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Prints confirmation that a task has been marked as completed.
     *
     * @param task Task instance marked as completed.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    /**
     * Prints confirmation that a task has been marked as not completed.
     *
     * @param task Task instance marked as not completed.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }
}
