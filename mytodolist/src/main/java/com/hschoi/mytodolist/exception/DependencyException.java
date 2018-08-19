package com.hschoi.mytodolist.exception;

public class DependencyException extends RuntimeException {
	public DependencyException () {

    }
    public DependencyException (String message) {
        super(message);
    }

}
