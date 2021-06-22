package com.joutvhu.date.parser.strategy;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StrategyFactory {
    public Strategy getStrategy(char c) {
        switch (c) {
            case 'a':
            case 'A':
                return new AmPmStrategy(c);
            case 'C':
                return new CenturyStrategy(c);
            case 'd':
            case 'D':
                return new DayStrategy(c);
            case 'e':
            case 'E':
                return new WeekdayStrategy(c);
            case 'F':
                return new WeekdayInMonthStrategy(c);
            case 'G':
                return new EraStrategy(c);
            case 'h':
            case 'H':
            case 'k':
            case 'K':
                return new HourStrategy(c);
            case 'm':
                return new MinuteStrategy(c);
            case 'M':
                return new MonthStrategy(c);
            case 's':
                return new SecondStrategy(c);
            case 'S':
                return new MillisecondStrategy(c);
            case 'Q':
                return new QuarterStrategy(c);
            case 'w':
            case 'W':
                return new WeekStrategy(c);
            case 'y':
            case 'Y':
                return new YearStrategy(c);
            case 'z':
            case 'Z':
                return new ZoneStrategy(c);
            default:
                return new QuotedStrategy(c);
        }
    }
}
