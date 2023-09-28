package com.teamvoy.testJava.exceptions;

public class CustomException extends Exception {
    public final int code;

    public CustomException(int code, String message) {
        super(message);
        this.code = code;
    }
}
