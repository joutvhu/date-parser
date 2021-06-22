package com.joutvhu.date.parser.strategy;

public class MinuteStrategy extends Strategy {
    public MinuteStrategy(char c) {
        super(c);
    }

    @Override
    public boolean add(char c) {
        return add(c == 'm', c);
    }
}
