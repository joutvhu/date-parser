package com.joutvhu.date.parser.strategy;

import com.joutvhu.date.parser.domain.DateStorage;
import com.joutvhu.date.parser.domain.StringSource;

public class YearStrategy extends Strategy {
    public YearStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == this.pattern.charAt(0), c);
    }

    @Override
    public void parse(DateStorage storage, StringSource source, NextStrategy chain) {
        int len = this.pattern.length();
        if (len == 4) {

        } else {
        }
        this.nextStrategy(chain);
    }
}
