package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateStorage;
import com.joutvhu.date.parser.domain.StringSource;

public class QuarterStrategy extends Strategy {
    public QuarterStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(false, c, c == 'o');
    }

    @Override
    public void parse(DateStorage storage, StringSource source, NextStrategy chain) {

    }
}
