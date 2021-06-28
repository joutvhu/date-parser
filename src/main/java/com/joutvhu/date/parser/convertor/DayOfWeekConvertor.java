package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;
import com.joutvhu.date.parser.strategy.WeekdayStrategy;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DayOfWeekConvertor implements Convertor<DayOfWeek> {
    public static final DayOfWeekConvertor INSTANCE = new DayOfWeekConvertor();

    @Override
    public DayOfWeek convert(ObjectiveDate objective) {
        if (objective.getYear() != null && objective.getMonth() != null && objective.getDay() != null)
            return LocalDate
                    .of(objective.getYear(), objective.getMonth(), objective.getDay())
                    .getDayOfWeek();

        Integer dayOfWeek = objective.get(WeekdayStrategy.WEEKDAY);
        if (dayOfWeek != null)
            return DayOfWeek
                    .of(dayOfWeek);

        throw new NullPointerException("Day of week is null");
    }
}
