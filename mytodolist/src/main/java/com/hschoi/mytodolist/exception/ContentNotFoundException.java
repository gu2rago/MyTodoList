package com.hschoi.mytodolist.exception;

public class ContentNotFoundException extends RuntimeException {
    public ContentNotFoundException () {
        super();
    }

    public ContentNotFoundException (String message) {
        super(message);
    }
}