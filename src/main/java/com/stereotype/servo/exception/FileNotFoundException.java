package com.stereotype.servo.exception;

public class FileNotFoundException extends ServoException {

    private static final String FILE_NOT_FOUND_EXCEPTION_CODE = "SE-1001";

    public FileNotFoundException() {
        super(FILE_NOT_FOUND_EXCEPTION_CODE);
    }

    public FileNotFoundException(String message) {
        super(FILE_NOT_FOUND_EXCEPTION_CODE, message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(FILE_NOT_FOUND_EXCEPTION_CODE, message, cause);
    }
}
