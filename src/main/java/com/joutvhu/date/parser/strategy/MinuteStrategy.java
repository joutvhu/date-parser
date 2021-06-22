package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.DateStorage;
import com.joutvhu.date.parser.StringSource;

public class MinuteStrategy extends Strategy {
    public MinuteStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == 'm', c);
    }

    @Override
    public void parse(DateStorage dateStorage, StringSource source, NextStrategy chain) {

    }
}
