package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.DateStorage;
import com.joutvhu.date.parser.StringSource;

public class AmPmStrategy extends Strategy {
    public AmPmStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return false;
    }

    @Override
    public void parse(DateStorage dateStorage, StringSource source, NextStrategy chain) {

    }
}
