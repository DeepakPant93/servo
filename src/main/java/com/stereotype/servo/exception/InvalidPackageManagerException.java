package com.stereotype.servo.exception;

public class InvalidPackageManagerException extends ServoException {

    private static final String CODE = "SE-1006";

    public InvalidPackageManagerException() {
        super(CODE);
    }

    public InvalidPackageManagerException(String message) {
        super(CODE, message);
    }

    public InvalidPackageManagerException(String message, Throwable cause) {
        super(CODE, message, cause);
    }
}
