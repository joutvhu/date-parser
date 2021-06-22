package com.joutvhu.date.parser.strategy;

public class YearStrategy extends Strategy {
    public YearStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c);
    }
}
