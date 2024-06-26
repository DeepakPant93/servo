package com.stereotype.servo.exception;

public class ServoException extends RuntimeException {

    public final String CODE;

    public ServoException(String code) {
        super();
        this.CODE = code;
    }

    public ServoException(String code, String message) {
        super(message);
        CODE = code;
    }

    public ServoException(String code, String message, Throwable cause) {
        super(message, cause);
        CODE = code;
    }

}
