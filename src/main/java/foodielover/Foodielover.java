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
            handleCommand(input);
            System.out.println(DIVIDER_LINE);
        }
    }

    /**
     * Dispatches user input to the corresponding action handler.
     *
     * @param input Raw command line entered by the user.
     */
    private static void handleCommand(String input) {
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
            System.out.println("This is not a valid input. Please try again. With the following: add, mark, unmark, todo, deadline, event, list");
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
     */
    private static void markTask(String input) {
        Integer taskIndex = parseTaskIndex(input, "mark");
        if (taskIndex == null) {
            return;
        }
        if (taskIndex >= 0 && taskIndex < taskCount) {
            tasks[taskIndex].markAsDone();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + tasks[taskIndex]);
        } else {
            System.out.println("This is not a valid task number. Please enter a number from 1 to " + taskCount + ".");
        }
    }

    /**
     * Marks the specified task as not completed.
     *
     * @param input Command string containing the 1-based task index.
     */
    private static void unmarkTask(String input) {
        Integer taskIndex = parseTaskIndex(input, "unmark");
        if (taskIndex == null) {
            return;
        }
        if (taskIndex >= 0 && taskIndex < taskCount) {
            tasks[taskIndex].markAsUndone();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + tasks[taskIndex]);
        } else {
            System.out.println("This is not a valid task number. Please enter a number from 1 to " + taskCount + ".");
        }
    }

    /**
     * Parses and adds a Todo task.
     *
     * @param input Command string containing the todo description.
     */
    private static void addTodo(String input) {
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            System.out.println("Please enter a description after 'todo'.");
            return;
        }
        addTask(new Todo(description));
    }

    /**
     * Parses and adds a Deadline task.
     *
     * @param input Command string containing the deadline description and '/by' parameter.
     */
    private static void addDeadline(String input) {
        int byIndex = input.indexOf("/by");
        if (byIndex < 0) {
            System.out.println("Please include a deadline using '/by'.");
            return;
        }
        String description = input.substring(8, byIndex).trim();
        String by = input.substring(byIndex + 3).trim();
        if (description.isEmpty() || by.isEmpty()) {
            System.out.println("Please provide both a deadline description and a value after '/by'.");
            return;
        }
        addTask(new Deadline(description, by));
    }

    /**
     * Parses and adds an Event task.
     *
     * @param input Command string containing description, '/from', and '/to' parameters.
     */
    private static void addEvent(String input) {
        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");
        if (fromIndex < 0 || toIndex < 0 || fromIndex >= toIndex) {
            System.out.println("Please include an event description, '/from' time, and '/to' time.");
            return;
        }
        String description = input.substring(5, fromIndex).trim();
        String from = input.substring(fromIndex + 5, toIndex).trim();
        String to = input.substring(toIndex + 3).trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            System.out.println("Please provide an event description and values after '/from' and '/to'.");
            return;
        }
        addTask(new Event(description, from, to));
    }

    /**
     * Stores a typed task, increments the count, and prints confirmation.
     *
     * @param task Task instance to be added.
     */
    private static void addTask(Task task) {
        if (taskCount >= MAX_TASKS) {
            System.out.println("Your task list is full. Please remove a task before adding another one.");
            return;
        }
        tasks[taskCount] = task;
        taskCount++;
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Adds a generic task when no command keyword matches.
     *
     * @param input Raw task description.
     */
    private static void addGenericTask(String input) {
        tasks[taskCount] = new Task(input);
        taskCount++;
        System.out.println("added: " + input);
    }

    /**
     * Parses a 1-based task number from a mark or unmark command.
     *
     * @param input Command containing the task number.
     * @param command Command keyword used in the input.
     * @return Zero-based task index, or null when the input is invalid.
     */
    private static Integer parseTaskIndex(String input, String command) {
        String argument = input.substring(command.length()).trim();
        if (argument.isEmpty()) {
            System.out.println("Please enter something after '" + command + "'.");
            return null;
        }
        try {
            return Integer.parseInt(argument) - 1;
        } catch (NumberFormatException exception) {
            System.out.println("Please enter a number after '" + command + "'.");
            return null;
        }
    }
}