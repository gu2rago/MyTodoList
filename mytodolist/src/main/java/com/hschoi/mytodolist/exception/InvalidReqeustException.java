package com.hschoi.mytodolist.exception;

public class InvalidReqeustException extends RuntimeException {

	public InvalidReqeustException () {

    }
    public InvalidReqeustException (String message) {
        super(message);
    }
}
