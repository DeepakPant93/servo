package com.stereotype.servo.exception;

public class ServoException extends RuntimeException {

    public final String code;

    public ServoException(String code) {
        super();
        this.code = code;
    }

    public ServoException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ServoException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
