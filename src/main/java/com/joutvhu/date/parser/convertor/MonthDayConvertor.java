package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

import java.time.Month;
import java.time.MonthDay;
import java.util.Objects;

public class MonthDayConvertor implements Convertor<MonthDay> {
    public static final MonthDayConvertor INSTANCE = new MonthDayConvertor();

    @Override
    public MonthDay convert(DateBuilder builder) {
        Objects.requireNonNull(builder.getMonth());
        Objects.requireNonNull(builder.getDay());

        return MonthDay.of(builder.getMonth(), builder.getDay());
    }
}
