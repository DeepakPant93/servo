package com.stereotype.servo.exception;

public class FileNotFoundException extends ServoException {

    private static final String CODE = "SE-1001";

    public FileNotFoundException() {
        super(CODE);
    }

    public FileNotFoundException(String message) {
        super(CODE, message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(CODE, message, cause);
    }
}
