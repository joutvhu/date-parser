package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.MonthDay;
import java.util.Objects;

public class MonthDayConvertor implements Convertor<MonthDay> {
    public static final MonthDayConvertor INSTANCE = new MonthDayConvertor();

    @Override
    public MonthDay convert(ObjectiveDate objective) {
        Objects.requireNonNull(objective.getMonth());
        Objects.requireNonNull(objective.getDay());

        return MonthDay.of(objective.getMonth(), objective.getDay());
    }
}
