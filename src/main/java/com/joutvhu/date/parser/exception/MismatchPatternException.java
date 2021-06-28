package com.joutvhu.date.parser.exception;

import lombok.Getter;

@Getter
public class MismatchPatternException extends RuntimeException {
    private static final long serialVersionUID = 4307834084681642132L;

    private final int position;
    private final String pattern;

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
