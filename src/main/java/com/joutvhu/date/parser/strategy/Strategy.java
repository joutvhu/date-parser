package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.domain.StringSource;

public abstract class Strategy {
    private boolean end;
    protected String pattern;

    public Strategy(char c) {
        this.end = false;
        this.pattern = String.valueOf(c);
    }

    public void afterPatternSet() {
        // Do nothing
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

    public abstract void parse(ObjectiveDate objective, StringSource source, NextStrategy chain);
}
