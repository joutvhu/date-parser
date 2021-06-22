package com.joutvhu.date.parser.strategy;

public class AmPmStrategy extends Strategy {
    public AmPmStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return false;
    }
}
