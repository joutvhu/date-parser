package com.joutvhu.date.parser.strategy;

public class MonthStrategy extends Strategy {
    public MonthStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == 'M', c, c == 'o');
    }
}
