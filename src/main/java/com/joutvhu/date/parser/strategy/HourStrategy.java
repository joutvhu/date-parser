package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.DateStorage;
import com.joutvhu.date.parser.StringSource;

public class HourStrategy extends Strategy {
    private boolean hour24;
    private boolean startFrom0;

    public HourStrategy(char c) {
        super(c);
        this.hour24 = c == 'H' || c == 'k';
        this.startFrom0 = c == 'H' || c == 'K';
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c);
    }

    @Override
    public void parse(DateStorage dateStorage, StringSource source, NextStrategy chain) {

    }
}
