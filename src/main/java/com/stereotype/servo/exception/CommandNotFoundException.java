package com.stereotype.servo.exception;

public class CommandNotFoundException extends ServoException {

    private static final String CODE = "SE-1005";

    public CommandNotFoundException() {
        super(CODE);
    }

    public CommandNotFoundException(String message) {
        super(CODE, message);
    }

    public CommandNotFoundException(String message, Throwable cause) {
        super(CODE, message, cause);
    }
}
