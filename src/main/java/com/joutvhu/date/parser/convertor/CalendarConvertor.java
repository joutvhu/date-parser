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
                builder.getMonth(),
                builder.getDay()
        );

        if (builder.getHour() != null)
            calendar.set(Calendar.HOUR, builder.getHour());
        if (builder.getMinute() != null)
            calendar.set(Calendar.MINUTE, builder.getMinute());
        if (builder.getSecond() != null)
            calendar.set(Calendar.SECOND, builder.getSecond());
        if (builder.getNano() != null)
            calendar.set(Calendar.MILLISECOND, builder.getNano() / 1000);

        return calendar;
    }
}
