package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.YearMonth;
import java.util.Objects;

public class YearMonthConvertor implements Convertor<YearMonth> {
    public static final YearMonthConvertor INSTANCE = new YearMonthConvertor();

    @Override
    public YearMonth convert(ObjectiveDate objective) {
        Objects.requireNonNull(objective.getYear());
        Objects.requireNonNull(objective.getMonth());

        return YearMonth.of(objective.getYear(), objective.getMonth());
    }
}
