package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.ObjectiveDate;

import java.util.Date;

public class DateConvertor implements Convertor<Date> {
    public static final DateConvertor INSTANCE = new DateConvertor();

    @Override
    public Date convert(ObjectiveDate objective) {
        return CalendarConvertor.INSTANCE.convert(objective).getTime();
    }
}
