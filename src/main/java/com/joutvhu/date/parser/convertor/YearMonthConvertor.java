package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.time.YearMonth;
import java.util.Objects;

public class YearMonthConvertor implements Convertor<YearMonth> {
    private static YearMonthConvertor instance;

    public static synchronized YearMonthConvertor getInstance() {
        if (instance == null)
            instance = new YearMonthConvertor();
        return instance;
    }

    @Override
    public YearMonth convert(ObjectiveDate objective) {
        Objects.requireNonNull(objective.getYear());
        Objects.requireNonNull(objective.getMonth());

        return YearMonth.of(objective.getYear(), objective.getMonth());
    }

    @Override
    public ObjectiveDate convert(ObjectiveDate objective, YearMonth object) {
        return objective;
    }
}
