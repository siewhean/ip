package foodielover.command;

import foodielover.FoodieloverException;
import foodielover.storage.Storage;
import foodielover.task.TaskList;
import foodielover.ui.Ui;

/**
 * Represents an executable user command.
 */
public abstract class Command {
    /**
     * Executes the command using the provided task list, user interface, and storage.
     *
     * @param tasks Current task list.
     * @param ui User interface for user interactions.
     * @param storage Storage handler for persisting changes.
     * @throws FoodieloverException If a command execution error occurs.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws FoodieloverException;

    /**
     * Indicates whether this command causes the application to terminate.
     *
     * @return True if the application should terminate, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
