package com.joutvhu.date.parser.exception;

import lombok.Getter;

@Getter
public class ConflictDateException extends RuntimeException {
    private static final long serialVersionUID = -4791053116414709832L;

    private final Object[] conflict;

    public ConflictDateException(String message, Object... conflict) {
        super(message);
        this.conflict = conflict;
    }

    public ConflictDateException(String message, Throwable cause, Object... conflict) {
        super(message, cause);
        this.conflict = conflict;
    }
}
