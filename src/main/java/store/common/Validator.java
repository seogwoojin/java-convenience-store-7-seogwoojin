package store.common;

import static store.common.ExceptionMessage.PROMOTION_VALIDATION_ERROR;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Validator {
    public static int validateNumber(String string) {
        try {
            int number = Integer.parseInt(string);
            validateNegative(number);
            return number;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    private static void validateNegative(int number) {
        if (number < 0) {
            throw new IllegalArgumentException();
        }
    }

    public static LocalDate validateDate(String dateString) {
        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(PROMOTION_VALIDATION_ERROR);
        }
    }
}
