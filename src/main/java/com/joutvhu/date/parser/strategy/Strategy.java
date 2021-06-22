package com.joutvhu.date.parser.strategy;

public abstract class Strategy {
    private boolean end;
    protected String pattern;

    public Strategy(char c) {
        this.end = false;
        this.pattern = String.valueOf(c);
    }

    public abstract boolean add(char c);

    protected boolean add(boolean condition, char c) {
        return add(condition, c, false);
    }

    protected boolean add(boolean condition, char c, boolean end) {
        if (this.end) {
            return false;
        } else if (end) {
            this.end = true;
            this.pattern += c;
            return true;
        } else if (condition) {
            this.pattern += c;
            return true;
        } else {
            return false;
        }
    }
}
