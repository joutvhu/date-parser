package com.joutvhu.date.parser.exception;

public class MismatchPatternException extends RuntimeException {
    private int position;
    private String pattern;

    public MismatchPatternException(String message, int position, String pattern) {
        super(message);
        this.position = position;
        this.pattern = pattern;
    }

    public MismatchPatternException(String message, Throwable cause, int position, String pattern) {
        super(message, cause);
        this.position = position;
        this.pattern = pattern;
    }
}
