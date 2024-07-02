package com.stereotype.servo.exception;

public class CommandNotFoundException extends ServoException {

    private static final String COMMAND_NOT_FOUND_EXCEPTION_CODE = "SE-1005";

    public CommandNotFoundException() {
        super(COMMAND_NOT_FOUND_EXCEPTION_CODE);
    }

    public CommandNotFoundException(String message) {
        super(COMMAND_NOT_FOUND_EXCEPTION_CODE, message);
    }

    public CommandNotFoundException(String message, Throwable cause) {
        super(COMMAND_NOT_FOUND_EXCEPTION_CODE, message, cause);
    }
}
