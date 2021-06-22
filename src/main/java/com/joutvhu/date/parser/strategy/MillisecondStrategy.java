package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateStorage;
import com.joutvhu.date.parser.domain.StringSource;

public class MillisecondStrategy extends Strategy {
    public MillisecondStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == 'S', c);
    }

    @Override
    public void parse(DateStorage storage, StringSource source, NextStrategy chain) {
        this.nextStrategy(chain);
    }
}
