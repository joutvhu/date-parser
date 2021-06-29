package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.util.Date;

public class DateConvertor implements Convertor<Date> {
    private static DateConvertor instance;

    public static synchronized DateConvertor getInstance() {
        if (instance == null)
            instance = new DateConvertor();
        return instance;
    }

    @Override
    public Date convert(ObjectiveDate objective) {
        return CalendarConvertor.getInstance().convert(objective).getTime();
    }
}
