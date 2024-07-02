package com.stereotype.servo.exception;

public class ConfigNotFoundException extends ServoException {

    private static final String CONFIG_NOT_FOUND_EXCEPTION_CODE = "SE-1002";

    public ConfigNotFoundException() {
        super(CONFIG_NOT_FOUND_EXCEPTION_CODE);
    }

    public ConfigNotFoundException(String message) {
        super(CONFIG_NOT_FOUND_EXCEPTION_CODE, message);
    }

    public ConfigNotFoundException(String message, Throwable cause) {
        super(CONFIG_NOT_FOUND_EXCEPTION_CODE, message, cause);
    }
}
