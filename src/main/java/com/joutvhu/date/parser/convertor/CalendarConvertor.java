package com.joutvhu.date.parser.convertor;

import com.joutvhu.date.parser.domain.DateBuilder;

import java.util.Calendar;
import java.util.Objects;

public class CalendarConvertor implements Convertor<Calendar> {
    public static final CalendarConvertor INSTANCE = new CalendarConvertor();

    @Override
    public Calendar convert(DateBuilder builder) {
        Objects.requireNonNull(builder.getZone());
        Objects.requireNonNull(builder.getLocale());
        Objects.requireNonNull(builder.getYear());
        Objects.requireNonNull(builder.getMonth());
        Objects.requireNonNull(builder.getDay());

        Calendar calendar = Calendar.getInstance(builder.getZone(), builder.getLocale());
        calendar.set(
                builder.getYear(),
                builder.getMonth() - 1,
                builder.getDay(),
                builder.getHour() != null ? builder.getHour() : 0,
                builder.getMinute() != null ? builder.getMinute() : 0,
                builder.getSecond() != null ? builder.getSecond() : 0
        );
        calendar.set(Calendar.MILLISECOND, builder.getNano() != null ? builder.getNano() / 1000000 : 0);

        return calendar;
    }
}
