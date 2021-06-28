package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.LocalTime;
import java.util.Objects;

public class LocalTimeConvertor implements Convertor<LocalTime> {
    public static final LocalTimeConvertor INSTANCE = new LocalTimeConvertor();

    @Override
    public LocalTime convert(ObjectiveDate objective) {
        Objects.requireNonNull(objective.getHour());
        Objects.requireNonNull(objective.getMinute());

        LocalTime localTime = LocalTime.of(objective.getHour(), objective.getMinute());
        if (objective.getSecond() != null)
            localTime = localTime.withSecond(objective.getSecond());
        if (objective.getNano() != null)
            localTime = localTime.withNano(objective.getNano());

        return localTime;
    }
}
