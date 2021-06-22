package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateStorage;
import com.joutvhu.date.parser.domain.StringSource;

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

    @Override
    public void parse(DateStorage storage, StringSource source, NextStrategy chain) {
        this.nextStrategy(chain);
    }
}
