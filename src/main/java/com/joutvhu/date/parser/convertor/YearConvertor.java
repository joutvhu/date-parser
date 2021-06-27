package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

import java.time.Year;
import java.util.Objects;

public class YearConvertor implements Convertor<Year> {
    public static final YearConvertor INSTANCE = new YearConvertor();

    @Override
    public Year convert(DateBuilder builder) {
        Objects.requireNonNull(builder.getYear());

        return Year.of(builder.getYear());
    }
}
