package com.joutvhu.date.parser.strategy;

public class WeekdayStrategy extends Strategy {
    private boolean text;

    public WeekdayStrategy(char c) {
        super(c);
        this.text = c == 'E';
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c);
    }
}
