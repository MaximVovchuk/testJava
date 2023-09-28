package com.teamvoy.testJava.exceptions;

public class Status420WrongPasswordException extends CustomException {
    public static final int CODE = 420;

    public Status420WrongPasswordException() {
        super(CODE,"Wrong password!");
    }
}
