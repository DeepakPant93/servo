package com.stereotype.servo.exception;

public class ProcessInterruptedException extends ServoException {

    private static final String CODE = "SE-1003";

    public ProcessInterruptedException() {
        super(CODE);
    }

    public ProcessInterruptedException(String message) {
        super(CODE, message);
    }

    public ProcessInterruptedException(String message, Throwable cause) {
        super(CODE, message, cause);
    }
}
