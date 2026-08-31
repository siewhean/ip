import java.util.Scanner;

/**
 * Entry point for the Foodielover chatbot application.
 */
public class Foodielover {
    /** Divider line used to format output messages. */
    private static final String DIVIDER_LINE = "____________________________________________________________";

    /**
     * Runs the Foodielover application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        String banner = " ______              _ _      _                            \n"
                + "|  ____|            | (_)    | |                           \n"
                + "| |__ ___   ___   __| |_  ___| | _____   _____ _ __        \n"
                + "|  __/ _ \\ / _ \\ / _` | |/ _ \\ |/ _ \\ \\ / / _ \\ '__|       \n"
                + "| | | (_) | (_) | (_| | |  __/ | (_) \\ V /  __/ |          \n"
                + "|_|  \\___/ \\___/ \\__,_|_|\\___|_|\\___/ \\_/ \\___|_|          \n";

        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println(DIVIDER_LINE);
        System.out.println(banner);
        System.out.println("Hello! I'm Foodielover.");
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER_LINE);

        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (!input.equals("bye")) {
            input = scanner.nextLine();
            System.out.println(DIVIDER_LINE);

            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ".[" + tasks[i].getStatusIcon() + "] " + tasks[i].getDescription());
                }
            } else if (input.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(input.substring(5).trim()) - 1;
                if (taskIndex >= 0 && taskIndex < taskCount) {
                    tasks[taskIndex].markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  [" + tasks[taskIndex].getStatusIcon() + "] "
                            + tasks[taskIndex].getDescription());
                }
            } else if (input.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(input.substring(7).trim()) - 1;
                if (taskIndex >= 0 && taskIndex < taskCount) {
                    tasks[taskIndex].markAsUndone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  [" + tasks[taskIndex].getStatusIcon() + "] "
                            + tasks[taskIndex].getDescription());
                }
            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println("added: " + input);
            }

            System.out.println(DIVIDER_LINE);
        }

        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(DIVIDER_LINE);
    }
}