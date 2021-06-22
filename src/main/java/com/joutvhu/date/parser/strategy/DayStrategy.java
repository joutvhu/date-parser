package com.joutvhu.date.parser.strategy;

public class DayStrategy extends Strategy {
    private boolean dayInYear;

    public DayStrategy(char c) {
        super(c);
        this.dayInYear = c == 'D';
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c, c == 'o');
    }
}
