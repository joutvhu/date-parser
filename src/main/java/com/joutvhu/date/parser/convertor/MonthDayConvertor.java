package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.MonthDay;
import java.util.Objects;

public class MonthDayConvertor implements Convertor<MonthDay> {
    private static MonthDayConvertor instance;

    public static synchronized MonthDayConvertor getInstance() {
        if (instance == null)
            instance = new MonthDayConvertor();
        return instance;
    }

    @Override
    public MonthDay convert(ObjectiveDate objective) {
        Objects.requireNonNull(objective.getMonth());
        Objects.requireNonNull(objective.getDay());

        return MonthDay.of(objective.getMonth(), objective.getDay());
    }

    @Override
    public ObjectiveDate convert(ObjectiveDate objective, MonthDay object) {
        if (object != null) {
            objective.setMonth(object.getMonthValue());
            objective.setDay(object.getDayOfMonth());
        }
        return objective;
    }
}
