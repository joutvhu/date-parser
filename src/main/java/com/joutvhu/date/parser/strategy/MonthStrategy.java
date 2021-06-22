package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.DateStorage;
import com.joutvhu.date.parser.StringSource;

public class MonthStrategy extends Strategy {
    public MonthStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == 'M', c, c == 'o');
    }

    @Override
    public void parse(DateStorage dateStorage, StringSource source, NextStrategy chain) {

    }
}
