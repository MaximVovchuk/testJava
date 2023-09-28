package com.teamvoy.testJava.exceptions;

public class Status421EmailBusyException extends CustomException {
    public static final int CODE = 421;

    public Status421EmailBusyException() {
        super(CODE, "This email is already taken");
    }
}
