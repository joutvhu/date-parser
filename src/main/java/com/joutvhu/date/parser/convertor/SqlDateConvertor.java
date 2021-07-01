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
        if (object != null)
            LocalDateConvertor.getInstance().convert(objective, object.toLocalDate());
        return objective;
    }
}
