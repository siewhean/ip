package foodielover;

import java.util.Scanner;

import foodielover.task.Deadline;
import foodielover.task.Event;
import foodielover.task.Task;
import foodielover.task.Todo;

/**
 * Entry point for the Foodielover chatbot application.
 */
public class Foodielover {
    /** Divider line used to format output messages. */
    private static final String DIVIDER_LINE = "____________________________________________________________";

    /** Banner graphic displayed upon startup. */
    private static final String BANNER = " ______              _ _      _                            \n"
            + "|  ____|            | (_)    | |                           \n"
            + "| |__ ___   ___   __| |_  ___| | _____   _____ _ __        \n"
            + "|  __/ _ \\ / _ \\ / _` | |/ _ \\ |/ _ \\ \\ / / _ \\ '__|       \n"
            + "| | | (_) | (_) | (_| | |  __/ | (_) \\ V /  __/ |          \n"
            + "|_|  \\___/ \\___/ \\__,_|_|\\___|_|\\___/ \\_/ \\___|_|          \n";

    /** Maximum capacity of tasks supported by the list. */
    private static final int MAX_TASKS = 100;

    /** In-memory storage for tasks. */
    private static final Task[] tasks = new Task[MAX_TASKS];

    /** Number of tasks currently in the list. */
    private static int taskCount = 0;

    /**
     * Runs the Foodielover application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        printGreeting();
        runCommandLoop();
        printGoodbye();
    }

    /**
     * Prints the startup banner and welcome greeting.
     */
    private static void printGreeting() {
        System.out.println(DIVIDER_LINE);
        System.out.println(BANNER);
        System.out.println("Hello! I'm Foodielover.");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER_LINE);
    }

    /**
     * Prints the exit farewell message.
     */
    private static void printGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(DIVIDER_LINE);
    }

    /**
     * Reads and processes user commands until the exit command is received.
     */
    private static void runCommandLoop() {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (!input.equals("bye") && scanner.hasNextLine()) {
            input = scanner.nextLine();
            System.out.println(DIVIDER_LINE);

            if (input.equals("bye")) {
                break;
            }
            try {
                handleCommand(input);
            } catch (FoodieloverException exception) {
                System.out.println(exception.getMessage());
            }
            System.out.println(DIVIDER_LINE);
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
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + "." + tasks[i]);
        }
    }

    /**
     * Marks the specified task as completed.
     *
     * @param input Command string containing the 1-based task index.
     * @throws FoodieloverException If the task index is missing, non-numeric, or out of range.
     */
    private static void markTask(String input) throws FoodieloverException {
        int taskIndex = parseTaskIndex(input, "mark");
        if (taskIndex < 0 || taskIndex >= taskCount) {
            throw new FoodieloverException(
                    "This is not a valid task number. Please enter a number from 1 to " + taskCount + ".");
        }
        tasks[taskIndex].markAsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + tasks[taskIndex]);
    }

    /**
     * Marks the specified task as not completed.
     *
     * @param input Command string containing the 1-based task index.
     * @throws FoodieloverException If the task index is missing, non-numeric, or out of range.
     */
    private static void unmarkTask(String input) throws FoodieloverException {
        int taskIndex = parseTaskIndex(input, "unmark");
        if (taskIndex < 0 || taskIndex >= taskCount) {
            throw new FoodieloverException(
                    "This is not a valid task number. Please enter a number from 1 to " + taskCount + ".");
        }
        tasks[taskIndex].markAsUndone();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + tasks[taskIndex]);
    }

    /**
     * Parses and adds a Todo task.
     *
     * @param input Command string containing the todo description.
     * @throws FoodieloverException If the todo description is empty or list is full.
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
     * @throws FoodieloverException If '/by' is missing, fields are empty, or list is full.
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
     * @throws FoodieloverException If delimiters are missing, fields are empty, or list is full.
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
     * Stores a typed task, increments the count, and prints confirmation.
     *
     * @param task Task instance to be added.
     * @throws FoodieloverException If maximum task storage capacity is reached.
     */
    private static void addTask(Task task) throws FoodieloverException {
        if (taskCount >= MAX_TASKS) {
            throw new FoodieloverException(
                    "Your task list is full. Please remove a task before adding another one.");
        }
        tasks[taskCount] = task;
        taskCount++;
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
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