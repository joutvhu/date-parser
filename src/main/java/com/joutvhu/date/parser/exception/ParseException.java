package com.joutvhu.date.parser.exception;

import lombok.Getter;

@Getter
public class ParseException extends RuntimeException {
    private static final long serialVersionUID = -2340887372568868746L;

    private final String[] patterns;

    public ParseException(String message, String... patterns) {
        super(message);
        this.patterns = patterns;
    }

    public ParseException(String message, Throwable cause, String... patterns) {
        super(message, cause);
        this.patterns = patterns;
    }

    public ParseException(Throwable cause, String... patterns) {
        super(cause);
        this.patterns = patterns;
    }

    public ParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String... patterns) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.patterns = patterns;
    }
}
