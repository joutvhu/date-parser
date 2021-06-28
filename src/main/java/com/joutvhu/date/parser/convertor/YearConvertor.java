package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.Year;
import java.util.Objects;

public class YearConvertor implements Convertor<Year> {
    public static final YearConvertor INSTANCE = new YearConvertor();

    @Override
    public Year convert(ObjectiveDate objective) {
        Objects.requireNonNull(objective.getYear());

        return Year.of(objective.getYear());
    }
}
