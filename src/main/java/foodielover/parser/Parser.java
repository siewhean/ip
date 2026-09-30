package foodielover.parser;

import java.time.LocalDate;

import foodielover.FoodieloverException;
import foodielover.command.AddCommand;
import foodielover.command.Command;
import foodielover.command.DateFilterCommand;
import foodielover.command.DeleteCommand;
import foodielover.command.ExitCommand;
import foodielover.command.FindCommand;
import foodielover.command.ListCommand;
import foodielover.command.MarkCommand;
import foodielover.command.UnmarkCommand;
import foodielover.task.Deadline;
import foodielover.task.Event;
import foodielover.task.Todo;

/**
 * Parses user input into executable Command objects.
 */
public class Parser {
    /**
     * Parses the user's raw input string and returns the corresponding Command.
     *
     * @param input Raw command line entered by the user.
     * @return Concrete Command corresponding to user input.
     * @throws FoodieloverException If command syntax is invalid or unrecognized.
     */
    public static Command parse(String input) throws FoodieloverException {
        if (input.equals("bye")) {
            return new ExitCommand();
        } else if (input.equals("list")) {
            return new ListCommand();
        } else if (input.equals("mark") || input.startsWith("mark ")) {
            int taskIndex = parseTaskIndex(input, "mark");
            return new MarkCommand(taskIndex);
        } else if (input.equals("unmark") || input.startsWith("unmark ")) {
            int taskIndex = parseTaskIndex(input, "unmark");
            return new UnmarkCommand(taskIndex);
        } else if (input.equals("todo") || input.startsWith("todo ")) {
            return parseTodo(input);
        } else if (input.equals("deadline") || input.startsWith("deadline ")) {
            return parseDeadline(input);
        } else if (input.equals("event") || input.startsWith("event ")) {
            return parseEvent(input);
        } else if (input.equals("delete") || input.startsWith("delete ")) {
            int taskIndex = parseTaskIndex(input, "delete");
            return new DeleteCommand(taskIndex);
        } else if (input.equals("find") || input.startsWith("find ")) {
            return parseFind(input);
        } else if (input.equals("date") || input.startsWith("date ")
                || input.equals("on") || input.startsWith("on ")) {
            return parseDateFilter(input);
        } else {
            throw new FoodieloverException(
                    "This is not a valid input. Please try again. "
                            + "With the following: add, mark, unmark, todo, deadline, event, list");
        }
    }

    /**
     * Parses a 1-based task number from a command string.
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

    /**
     * Parses a todo command string into an AddCommand.
     *
     * @param input Command string containing the todo description.
     * @return AddCommand wrapping the created Todo.
     * @throws FoodieloverException If description is empty.
     */
    private static Command parseTodo(String input) throws FoodieloverException {
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            throw new FoodieloverException("Please enter a description after 'todo'.");
        }
        return new AddCommand(new Todo(description));
    }

    /**
     * Parses a deadline command string into an AddCommand.
     *
     * @param input Command string containing description and '/by'.
     * @return AddCommand wrapping the created Deadline.
     * @throws FoodieloverException If '/by' is missing or fields are empty.
     */
    private static Command parseDeadline(String input) throws FoodieloverException {
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
        return new AddCommand(new Deadline(description, by));
    }

    /**
     * Parses an event command string into an AddCommand.
     *
     * @param input Command string containing description, '/from', and '/to'.
     * @return AddCommand wrapping the created Event.
     * @throws FoodieloverException If delimiters are missing or fields are empty.
     */
    private static Command parseEvent(String input) throws FoodieloverException {
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
        return new AddCommand(new Event(description, from, to));
    }

    /**
     * Parses a date filter command string into a DateFilterCommand.
     *
     * @param input Command string containing the command keyword and date.
     * @return DateFilterCommand with the parsed target date.
     * @throws FoodieloverException If date argument is missing or in an invalid format.
     */
    private static Command parseDateFilter(String input) throws FoodieloverException {
        String keyword = (input.equals("on") || input.startsWith("on ")) ? "on" : "date";
        String argument = input.substring(keyword.length()).trim();
        if (argument.isEmpty()) {
            throw new FoodieloverException(
                    "Please enter a date after '" + keyword + "' (e.g., 2019-10-15 or 2/12/2019).");
        }
        LocalDate date = DateTimeParser.parseDate(argument);
        if (date == null) {
            throw new FoodieloverException(
                    "Please enter a valid date in the format yyyy-mm-dd or d/M/yyyy (e.g., 2019-10-15 or 2/12/2019).");
        }
        return new DateFilterCommand(date);
    }

    /**
     * Parses a find command string into a FindCommand.
     *
     * @param input Command string containing the search keyword.
     * @return FindCommand with the parsed keyword.
     * @throws FoodieloverException If keyword is missing.
     */
    private static Command parseFind(String input) throws FoodieloverException {
        String keyword = input.substring(4).trim();
        if (keyword.isEmpty()) {
            throw new FoodieloverException("Please enter a keyword after 'find'.");
        }
        return new FindCommand(keyword);
    }
}
