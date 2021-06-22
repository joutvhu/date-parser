package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.DateStorage;
import com.joutvhu.date.parser.StringSource;

public class QuarterStrategy extends Strategy {
    public QuarterStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(false, c, c == 'o');
    }

    @Override
    public void parse(DateStorage dateStorage, StringSource source, NextStrategy chain) {

    }
}
