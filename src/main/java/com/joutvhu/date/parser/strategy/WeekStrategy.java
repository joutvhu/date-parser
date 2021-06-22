package com.joutvhu.date.parser.strategy;

public class WeekStrategy extends Strategy {
    private boolean weekInYear;

    public WeekStrategy(char c) {
        super(c);
        this.weekInYear = c == 'w';
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c, c == 'o');
    }
}
