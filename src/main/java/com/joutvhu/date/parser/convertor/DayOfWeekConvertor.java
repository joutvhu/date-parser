package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;
import com.joutvhu.date.parser.strategy.WeekdayStrategy;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DayOfWeekConvertor implements Convertor<DayOfWeek> {
    public static final DayOfWeekConvertor INSTANCE = new DayOfWeekConvertor();

    @Override
    public DayOfWeek convert(DateBuilder builder) {
        if (builder.getYear() != null && builder.getMonth() != null && builder.getDay() != null)
            return LocalDate
                    .of(builder.getYear(), builder.getMonth(), builder.getDay())
                    .getDayOfWeek();

        Integer dayOfWeek = builder.get(WeekdayStrategy.WEEKDAY);
        if (dayOfWeek != null)
            return DayOfWeek
                    .of(dayOfWeek);

        throw new NullPointerException("Day of week is null");
    }
}
