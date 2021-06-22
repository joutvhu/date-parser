package com.joutvhu.date.parser.strategy;

public class WeekdayInMonthStrategy extends Strategy {
    public WeekdayInMonthStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return false;
    }
}
