package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.DateStorage;
import com.joutvhu.date.parser.StringSource;

public class QuotedStrategy extends Strategy {
    private boolean quoted;
    private Boolean end;

    public QuotedStrategy(char c) {
        super(c);
        this.end = false;
        this.quoted = c == '\'';
        this.pattern = this.quoted ? "" : String.valueOf(c);
    }

    @Override
    public boolean add(char c) {
        if (this.quoted) {
            if (Boolean.TRUE.equals(this.end))
                return false;
            else {
                if (c == '\'') {
                    if (this.end == null) {
                        this.pattern += c;
                        this.end = false;
                    } else
                        this.end = null;
                    return true;
                } else {
                    if (this.end == null) {
                        this.end = true;
                        return false;
                    } else {
                        this.pattern += c;
                        return true;
                    }
                }
            }
        } else
            return false;
    }

    @Override
    public void parse(DateStorage dateStorage, StringSource source, NextStrategy chain) {
    }
}
