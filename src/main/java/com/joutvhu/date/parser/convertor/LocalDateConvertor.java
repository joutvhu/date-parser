package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.LocalDate;
import java.util.Objects;

public class LocalDateConvertor implements Convertor<LocalDate> {
    public static final LocalDateConvertor INSTANCE = new LocalDateConvertor();

    @Override
    public LocalDate convert(ObjectiveDate objective) {
        Objects.requireNonNull(objective.getYear());
        Objects.requireNonNull(objective.getMonth());
        Objects.requireNonNull(objective.getDay());

        return LocalDate.of(objective.getYear(), objective.getMonth(), objective.getDay());
    }
}
