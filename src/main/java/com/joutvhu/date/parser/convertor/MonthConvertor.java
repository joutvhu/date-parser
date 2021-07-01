package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.Month;
import java.util.Objects;

public class MonthConvertor implements Convertor<Month> {
    private static MonthConvertor instance;

    public static synchronized MonthConvertor getInstance() {
        if (instance == null)
            instance = new MonthConvertor();
        return instance;
    }

    @Override
    public Month convert(ObjectiveDate objective) {
        Objects.requireNonNull(objective.getMonth());

        return Month.of(objective.getMonth());
    }

    @Override
    public ObjectiveDate convert(ObjectiveDate objective, Month object) {
        return objective;
    }
}
