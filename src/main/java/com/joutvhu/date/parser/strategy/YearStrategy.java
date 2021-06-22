package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.DateStorage;
import com.joutvhu.date.parser.StringSource;

public class YearStrategy extends Strategy {
    public YearStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c);
    }

    @Override
    public void parse(DateStorage dateStorage, StringSource source, NextStrategy chain) {
        if (this.pattern.length() == 2) {
        } else {
        }
    }
}
