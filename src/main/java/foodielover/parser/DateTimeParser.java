package foodielover.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Parses and formats dates and times for tasks.
 */
public class DateTimeParser {
    /** Output formatter for dates. */
    private static final DateTimeFormatter DATE_OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);

    /** Output formatter for date-times. */
    private static final DateTimeFormatter DATE_TIME_OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma", Locale.ENGLISH);

    /** Supported input date-time formatters. */
    private static final DateTimeFormatter[] DATE_TIME_INPUT_FORMATTERS = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
            DateTimeFormatter.ofPattern("d/M/yyyy HH:mm"),
            DateTimeFormatter.ofPattern("d-M-yyyy HHmm"),
            DateTimeFormatter.ofPattern("d-M-yyyy HH:mm"),
            DateTimeFormatter.ofPattern("yyyy/M/d HHmm"),
            DateTimeFormatter.ofPattern("yyyy/M/d HH:mm")
    };

    /** Supported input date formatters. */
    private static final DateTimeFormatter[] DATE_INPUT_FORMATTERS = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("d/M/yyyy"),
            DateTimeFormatter.ofPattern("d-M-yyyy"),
            DateTimeFormatter.ofPattern("yyyy/M/d")
    };

    /**
     * Parses the input string into a LocalDateTime if it matches a supported date-time format.
     *
     * @param text Raw date-time string.
     * @return Parsed LocalDateTime, or null if no format matches.
     */
    public static LocalDateTime parseDateTime(String text) {
        if (text == null) {
            return null;
        }
        String trimmed = text.trim();
        for (DateTimeFormatter formatter : DATE_TIME_INPUT_FORMATTERS) {
            try {
                return LocalDateTime.parse(trimmed, formatter);
            } catch (DateTimeParseException ignored) {
                // Try next pattern
            }
        }
        return null;
    }

    /**
     * Parses the input string into a LocalDate if it matches a supported date format.
     *
     * @param text Raw date string.
     * @return Parsed LocalDate, or null if no format matches.
     */
    public static LocalDate parseDate(String text) {
        if (text == null) {
            return null;
        }
        String trimmed = text.trim();
        for (DateTimeFormatter formatter : DATE_INPUT_FORMATTERS) {
            try {
                return LocalDate.parse(trimmed, formatter);
            } catch (DateTimeParseException ignored) {
                // Try next pattern
            }
        }
        return null;
    }

    /**
     * Formats a LocalDate into the standard user-facing format (e.g. Oct 15 2019).
     *
     * @param date Date to format.
     * @return Formatted date string.
     */
    public static String formatDate(LocalDate date) {
        return date.format(DATE_OUTPUT_FORMATTER);
    }

    /**
     * Formats a LocalDateTime into the standard user-facing format (e.g. Dec 02 2019, 6:00PM).
     *
     * @param dateTime Date-time to format.
     * @return Formatted date-time string.
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_OUTPUT_FORMATTER);
    }
}
