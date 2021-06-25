package com.joutvhu.date.parser.exception;

public class ConflictDateException extends RuntimeException {
    private Object[] conflict;

    public ConflictDateException(String message, Object ...conflict) {
        super(message);
        this.conflict = conflict;
    }

    public ConflictDateException(String message, Throwable cause, Object ...conflict) {
        super(message, cause);
        this.conflict = conflict;
    }
}
