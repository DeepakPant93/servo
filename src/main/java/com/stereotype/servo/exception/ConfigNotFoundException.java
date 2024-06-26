package com.stereotype.servo.exception;

public class ConfigNotFoundException extends ServoException {

    private static final String CODE = "SE-1002";

    public ConfigNotFoundException() {
        super(CODE);
    }

    public ConfigNotFoundException(String message) {
        super(CODE, message);
    }

    public ConfigNotFoundException(String message, Throwable cause) {
        super(CODE, message, cause);
    }
}
