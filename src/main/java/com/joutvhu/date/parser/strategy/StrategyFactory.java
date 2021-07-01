package com.joutvhu.date.parser.strategy;

public class StrategyFactory {
    public static final StrategyFactory INSTANCE = new StrategyFactory();

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
            case 'u':
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
            case 'L':
                return new MonthStrategy(c);
            case 'n':
            case 'N':
                return new NanoStrategy(c);
            case 's':
                return new SecondStrategy(c);
            case 'S':
                return new MillisecondStrategy(c);
            case 'q':
            case 'Q':
                return new QuarterStrategy(c);
            case 'w':
            case 'W':
                return new WeekStrategy(c);
            case 'y':
            case 'Y':
                return new YearStrategy(c);
            case 'X':
            case 'z':
            case 'Z':
                return new ZoneStrategy(c);
            default:
                return new QuoteStrategy(c);
        }
    }
}
