package com.joutvhu.date.parser.exception;

public class MismatchException extends RuntimeException {
    private int position;
    private String pattern;

    public MismatchException(String message, int position, String pattern) {
        super(message);
        this.position = position;
        this.pattern = pattern;
    }

    public MismatchException(String message, Throwable cause, int position, String pattern) {
        super(message, cause);
        this.position = position;
        this.pattern = pattern;
    }
}
