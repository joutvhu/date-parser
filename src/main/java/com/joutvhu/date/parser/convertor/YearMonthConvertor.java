package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

import java.time.Year;
import java.time.YearMonth;
import java.util.Objects;

public class YearMonthConvertor implements Convertor<YearMonth> {
    public static final YearMonthConvertor INSTANCE = new YearMonthConvertor();

    @Override
    public YearMonth convert(DateBuilder builder) {
        Objects.requireNonNull(builder.getYear());
        Objects.requireNonNull(builder.getMonth());

        return YearMonth.of(builder.getYear(), builder.getMonth());
    }
}
