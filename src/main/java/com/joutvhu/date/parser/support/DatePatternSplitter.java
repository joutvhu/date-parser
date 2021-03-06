package com.joutvhu.date.parser.support;

import com.joutvhu.date.parser.strategy.Strategy;
import com.joutvhu.date.parser.strategy.StrategyFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DatePatternSplitter {
    private final String pattern;
    private final int length;

    private int position;
    private Strategy strategy;
    private StrategyFactory strategyFactory = StrategyFactory.INSTANCE;

    public DatePatternSplitter(String pattern) {
        Objects.requireNonNull(pattern, "Pattern must not be null.");

        this.pattern = pattern;
        this.position = 0;
        this.length = pattern.length();
    }

    public DatePatternSplitter(String pattern, StrategyFactory strategyFactory) {
        this(pattern);
        if (strategyFactory != null)
            this.strategyFactory = strategyFactory;
    }

    public Strategy getNextStrategy() {
        while (this.position < this.length) {
            char c = this.pattern.charAt(this.position);

            if (strategy == null) {
                this.strategy = this.strategyFactory.getStrategy(c);
                this.position++;
            } else if (strategy.add(c)) {
                this.position++;
            } else {
                Strategy result = this.strategy;
                this.strategy = this.strategyFactory.getStrategy(c);
                this.position++;
                result.afterPatternSet();
                return result;
            }
        }

        if (this.strategy != null) {
            Strategy result = this.strategy;
            this.strategy = null;
            result.afterPatternSet();
            return result;
        } else {
            return null;
        }
    }

    public List<Strategy> getStrategyChain() {
        List<Strategy> result = new ArrayList<>();
        while (true) {
            Strategy nextStrategy = this.getNextStrategy();
            if (nextStrategy != null)
                result.add(nextStrategy);
            else
                return result;
        }
    }
}
