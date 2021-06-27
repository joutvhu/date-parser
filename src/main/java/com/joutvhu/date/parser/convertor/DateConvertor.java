package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

import java.util.Date;

public class DateConvertor implements Convertor<Date> {
    public static final DateConvertor INSTANCE = new DateConvertor();

    @Override
    public Date convert(DateBuilder builder) {
        return CalendarConvertor.INSTANCE.convert(builder).getTime();
    }
}
