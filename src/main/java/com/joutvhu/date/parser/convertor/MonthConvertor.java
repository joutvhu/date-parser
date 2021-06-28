package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.Month;
import java.util.Objects;

public class MonthConvertor implements Convertor<Month> {
    public static final MonthConvertor INSTANCE = new MonthConvertor();

    @Override
    public Month convert(ObjectiveDate objective) {
        Objects.requireNonNull(objective.getMonth());

        return Month.of(objective.getMonth());
    }
}
