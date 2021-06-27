package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

import java.time.Month;
import java.time.YearMonth;
import java.util.Objects;

public class MonthConvertor implements Convertor<Month> {
    public static final MonthConvertor INSTANCE = new MonthConvertor();

    @Override
    public Month convert(DateBuilder builder) {
        Objects.requireNonNull(builder.getMonth());

        return Month.of(builder.getMonth());
    }
}
