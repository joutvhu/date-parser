package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.Year;
import java.util.Objects;

public class YearConvertor implements Convertor<Year> {
    private static YearConvertor instance;

    public static synchronized YearConvertor getInstance() {
        if (instance == null)
            instance = new YearConvertor();
        return instance;
    }

    @Override
    public Year convert(ObjectiveDate objective) {
        Objects.requireNonNull(objective.getYear());

        return Year.of(objective.getYear());
    }

    @Override
    public ObjectiveDate convert(ObjectiveDate objective, Year object) {
        return objective;
    }
}
