package foodielover.parser;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import foodielover.FoodieloverException;
import foodielover.command.AddCommand;
import foodielover.command.Command;
import foodielover.command.ExitCommand;
import foodielover.command.ListCommand;

public class ParserTest {

    @Test
    public void parse_validCommands_returnsCorrectCommandInstance() throws FoodieloverException {
        Command byeCmd = Parser.parse("bye");
        assertInstanceOf(ExitCommand.class, byeCmd);

        Command listCmd = Parser.parse("list");
        assertInstanceOf(ListCommand.class, listCmd);

        Command todoCmd = Parser.parse("todo read book");
        assertInstanceOf(AddCommand.class, todoCmd);
    }

    @Test
    public void parse_whitespaceAndCaseTolerance_success() throws FoodieloverException {
        assertInstanceOf(ExitCommand.class, Parser.parse("  BYE  "));
        assertInstanceOf(ListCommand.class, Parser.parse("  list  "));
        assertInstanceOf(AddCommand.class, Parser.parse("  TODO borrow book  "));
    }

    @Test
    public void parse_pipeInDescription_throwsException() {
        FoodieloverException e1 = assertThrows(FoodieloverException.class, () ->
                Parser.parse("todo read | book"));
        assertTrue(e1.getMessage().contains("pipe"));

        FoodieloverException e2 = assertThrows(FoodieloverException.class, () ->
                Parser.parse("deadline submit | report /by 2026-10-15"));
        assertTrue(e2.getMessage().contains("pipe"));

        FoodieloverException e3 = assertThrows(FoodieloverException.class, () ->
                Parser.parse("event carnival /from 2026-10-15 /to 2026-10-16 | party"));
        assertTrue(e3.getMessage().contains("pipe"));
    }

    @Test
    public void parse_impossibleDateInDeadline_throwsException() {
        FoodieloverException exception = assertThrows(FoodieloverException.class, () ->
                Parser.parse("deadline return book /by 2019-02-30"));
        assertTrue(exception.getMessage().contains("Invalid date or time"));
    }

    @Test
    public void parse_eventEndBeforeStart_throwsException() {
        FoodieloverException exception = assertThrows(FoodieloverException.class, () ->
                Parser.parse("event party /from 2019-12-02 1800 /to 2019-12-01 1800"));
        assertTrue(exception.getMessage().contains("cannot be earlier"));
    }

    @Test
    public void parse_emptyOrInvalidInput_throwsException() {
        assertThrows(FoodieloverException.class, () -> Parser.parse(""));
        assertThrows(FoodieloverException.class, () -> Parser.parse("   "));

        FoodieloverException exception = assertThrows(FoodieloverException.class, () ->
                Parser.parse("unknown-command"));
        assertTrue(exception.getMessage().contains("This is not a valid input"));
    }
}
