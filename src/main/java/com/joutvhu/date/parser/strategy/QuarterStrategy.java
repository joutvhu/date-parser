package com.joutvhu.date.parser.strategy;

public class QuarterStrategy extends Strategy {
    public QuarterStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(false, c, c == 'o');
    }
}
