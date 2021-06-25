package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateStorage;
import com.joutvhu.date.parser.domain.StringSource;
import com.joutvhu.date.parser.exception.MismatchException;

public class QuoteStrategy extends Strategy {
    private final boolean quoted;
    private Boolean end;

    public QuoteStrategy(char c) {
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
    public void parse(DateStorage storage, StringSource source, NextStrategy chain) {
        StringSource.PositionBackup backup = source.backup();
        String value = source.get(this.pattern.length());
        if (pattern.equals(value))
            this.nextStrategy(chain);
        else {
            backup.restore();
            throw new MismatchException(
                    "The quote \"" + value + "\" not match with \"" + this.pattern + "\".",
                    source.getIndex(),
                    this.pattern
            );
        }
    }
}
