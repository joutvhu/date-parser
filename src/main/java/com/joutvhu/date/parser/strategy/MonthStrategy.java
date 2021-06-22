package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateStorage;
import com.joutvhu.date.parser.domain.StringSource;

public class MonthStrategy extends Strategy {
    public MonthStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == 'M', c, c == 'o');
    }

    @Override
    public void parse(DateStorage storage, StringSource source, NextStrategy chain) {
        this.nextStrategy(chain);
    }
}
