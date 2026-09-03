import java.util.Scanner;

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

        while (!input.equals("bye")) {
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
        } else if (input.startsWith("mark ")) {
            markTask(input);
        } else if (input.startsWith("unmark ")) {
            unmarkTask(input);
        } else if (input.startsWith("todo")) {
            addTodo(input);
        } else if (input.startsWith("deadline")) {
            addDeadline(input);
        } else if (input.startsWith("event")) {
            addEvent(input);
        } else {
            addGenericTask(input);
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
        int taskIndex = Integer.parseInt(input.substring(5).trim()) - 1;
        if (taskIndex >= 0 && taskIndex < taskCount) {
            tasks[taskIndex].markAsDone();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + tasks[taskIndex]);
        }
    }

    /**
     * Marks the specified task as not completed.
     *
     * @param input Command string containing the 1-based task index.
     */
    private static void unmarkTask(String input) {
        int taskIndex = Integer.parseInt(input.substring(7).trim()) - 1;
        if (taskIndex >= 0 && taskIndex < taskCount) {
            tasks[taskIndex].markAsUndone();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + tasks[taskIndex]);
        }
    }

    /**
     * Parses and adds a Todo task.
     *
     * @param input Command string containing the todo description.
     */
    private static void addTodo(String input) {
        String description = input.substring(4).trim();
        addTask(new Todo(description));
    }

    /**
     * Parses and adds a Deadline task.
     *
     * @param input Command string containing the deadline description and '/by' parameter.
     */
    private static void addDeadline(String input) {
        int byIndex = input.indexOf("/by");
        String description = input.substring(8, byIndex).trim();
        String by = input.substring(byIndex + 3).trim();
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
        String description = input.substring(5, fromIndex).trim();
        String from = input.substring(fromIndex + 5, toIndex).trim();
        String to = input.substring(toIndex + 3).trim();
        addTask(new Event(description, from, to));
    }

    /**
     * Stores a typed task, increments the count, and prints confirmation.
     *
     * @param task Task instance to be added.
     */
    private static void addTask(Task task) {
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
}