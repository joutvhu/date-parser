package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.sql.Date;

public class SqlDateConvertor implements Convertor<Date> {
    private static SqlDateConvertor instance;

    public static synchronized SqlDateConvertor getInstance() {
        if (instance == null)
            instance = new SqlDateConvertor();
        return instance;
    }

    @Override
    public Date convert(ObjectiveDate objective) {
        return Date.valueOf(LocalDateConvertor.getInstance().convert(objective));
    }

    @Override
    public ObjectiveDate convert(ObjectiveDate objective, Date object) {
        if (object != null) {
            objective.setYear(object.getYear() + 1900);
            objective.setMonth(object.getMonth() + 1);
            objective.setDay(object.getDate());
        }
        return objective;
    }
}
