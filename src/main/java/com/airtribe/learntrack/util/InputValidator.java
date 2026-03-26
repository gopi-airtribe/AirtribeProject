package com.airtribe.learntrack.util;

import com.airtribe.learntrack.exception.InvalidInputException;

public final class InputValidator {
    private InputValidator() {
    }

    public static String requireNonBlank(String value, String fieldName) throws InvalidInputException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty.");
        }
        return value.trim();
    }

    public static int parsePositiveInt(String value, String fieldName) throws InvalidInputException {
        try {
            int parsedValue = Integer.parseInt(requireNonBlank(value, fieldName));
            if (parsedValue <= 0) {
                throw new InvalidInputException(fieldName + " must be greater than 0.");
            }
            return parsedValue;
        } catch (NumberFormatException exception) {
            throw new InvalidInputException("Please enter a valid number for " + fieldName + ".");
        }
    }

    public static String normalizeEmail(String email) {
        return email == null ? "" : email.trim();
    }
}

