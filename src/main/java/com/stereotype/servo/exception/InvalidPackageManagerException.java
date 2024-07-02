package com.stereotype.servo.exception;

public class InvalidPackageManagerException extends ServoException {

    private static final String INVALID_PACKAGE_MANAGER_EXCEPTION_CODE = "SE-1006";

    public InvalidPackageManagerException() {
        super(INVALID_PACKAGE_MANAGER_EXCEPTION_CODE);
    }

    public InvalidPackageManagerException(String message) {
        super(INVALID_PACKAGE_MANAGER_EXCEPTION_CODE, message);
    }

    public InvalidPackageManagerException(String message, Throwable cause) {
        super(INVALID_PACKAGE_MANAGER_EXCEPTION_CODE, message, cause);
    }
}
