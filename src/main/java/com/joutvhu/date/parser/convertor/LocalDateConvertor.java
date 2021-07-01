package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.LocalDate;
import java.util.Objects;

public class LocalDateConvertor implements Convertor<LocalDate> {
    private static LocalDateConvertor instance;

    public static synchronized LocalDateConvertor getInstance() {
        if (instance == null)
            instance = new LocalDateConvertor();
        return instance;
    }

    @Override
    public LocalDate convert(ObjectiveDate objective) {
        Objects.requireNonNull(objective.getYear());
        Objects.requireNonNull(objective.getMonth());
        Objects.requireNonNull(objective.getDay());

        return LocalDate.of(objective.getYear(), objective.getMonth(), objective.getDay());
    }

    @Override
    public ObjectiveDate convert(ObjectiveDate objective, LocalDate object) {
        return objective;
    }
}
